package com.waiter.server.services.venue.impl;

import com.waiter.server.persistence.core.repository.venue.VenueRepository;
import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceException;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import com.waiter.server.services.company.CompanyService;
import com.waiter.server.services.evaluation.model.Evaluation;
import com.waiter.server.services.event.ApplicationEventBus;
import com.waiter.server.services.gallery.GalleryImageService;
import com.waiter.server.services.gallery.dto.GalleryImageDto;
import com.waiter.server.services.gallery.model.Gallery;
import com.waiter.server.services.gallery.model.GalleryImage;
import com.waiter.server.services.gallery.model.GalleryImageType;
import com.waiter.server.services.gallery.model.ImageType;
import com.waiter.server.services.location.LocationService;
import com.waiter.server.services.menu.MenuService;
import com.waiter.server.services.menu.model.Menu;
import com.waiter.server.services.venue.VenueService;
import com.waiter.server.services.venue.dto.ScheduleDto;
import com.waiter.server.services.venue.dto.VenueDto;
import com.waiter.server.services.venue.event.VenueUpdateEvent;
import com.waiter.server.services.venue.model.Schedule;
import com.waiter.server.services.venue.model.Venue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.util.Assert.notNull;

/**
 * Created by Admin on 12/12/2015.
 */
@Service
public class VenueServiceImpl implements VenueService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VenueServiceImpl.class);

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ApplicationEventBus applicationEventBus;

    @Autowired
    private GalleryImageService galleryImageService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private CompanyService companyService;

    @Override
    public Venue getById(Long id) {
        assertVenueId(id);
        Venue venue = venueRepository.findOne(id);
        if (venue == null) {
            LOGGER.error("Venue with id -{} not found", id);
            throw new ServiceRuntimeException(ErrorCode.NOT_FOUND, "Venue not found");
        }
        return venue;
    }

    @Override
    public List<Venue> getAllByIds(List<Long> ids) {
        notNull(ids);
        List<Venue> venues = venueRepository.findAll(ids);
        return venues;
    }

    @Override
    @Transactional
    public Venue create(VenueDto venueDto) {
        notNull(venueDto.getName());
        notNull(venueDto.getCompanyId());
        notNull(venueDto.getLocationId());
        final Venue venue = new Venue();
        venue.setName(venueDto.getName());
        if (venueDto.getMenuId() != null) {
            venue.setMenu(menuService.getById(venueDto.getMenuId()));
        }
        venue.setLocation(locationService.getById(venueDto.getLocationId()));
        venue.setCompany(companyService.get(venueDto.getCompanyId()));
        venue.setGallery(new Gallery());
        venue.setEvaluation(new Evaluation());
        venue.setSource(venueDto.getSourceUrl());
        updateOpeningHours(venue, venueDto.getOpenHours());

        final Venue createdVenue = venueRepository.save(venue);
        LOGGER.debug("Venue -{} successfully stored", venue);
        applicationEventBus.publishAsynchronousEvent(new VenueUpdateEvent(createdVenue));
        return createdVenue;
    }

    @Override
    @Transactional
    public Venue update(Long id, String name, Long menuId) {
        assertVenueId(id);
        final Venue venue = getById(id);
        if (menuId != null) {
            Menu menu = menuService.getById(menuId);
            venue.setMenu(menu);
        }
        if (name != null) {
            venue.setName(name);
        }
        final Venue updatedVenue = venueRepository.save(venue);
        applicationEventBus.publishAsynchronousEvent(new VenueUpdateEvent(updatedVenue));
        return updatedVenue;
    }

    @Override
    public GalleryImage addImage(Long venueId, InputStream inputStream, GalleryImageType galleryImageType, ImageType imageType) throws ServiceException {
        Venue venue = getById(venueId);
        final GalleryImageDto galleryImageDto = new GalleryImageDto();
        galleryImageDto.setGalleryImageType(galleryImageType);
        galleryImageDto.setImageType(imageType);
        galleryImageDto.setFileName("venue");
        if (venue.getGallery() == null) {
            venue.setGallery(new Gallery());
            venue = venueRepository.save(venue);
        }
        final GalleryImage galleryImage = galleryImageService.addImage(venue.getGallery().getId(), galleryImageDto, inputStream);
        return galleryImage;
    }

    @Override
    public GalleryImage addImage(Long venueId, InputStream inputStream) throws ServiceException {
        return addImage(venueId, inputStream, GalleryImageType.MAIN, ImageType.JPEG);
    }

    public List<Venue> attacheMenuToVenues(Set<Long> venueIds, Long menuId) {
        final Menu menu = menuService.getById(menuId);
        final List<Venue> venues = venueRepository.findAll(venueIds);
        venues.forEach(venue -> {
            if (venue.getMenu() != null) {
                LOGGER.error("Menu already attached to venue -{}", venue.getId());
                throw new ServiceRuntimeException(ErrorCode.BAD_REQUEST, "Menu already attached to venue");
            }
            venue.setMenu(menu);
        });
        return venueRepository.save(venues);
    }

    public Venue updateAttachmentOfMenu(Long venueId, Long menuId) {
        final Venue venue = getById(venueId);
        final Menu menu = menuService.getById(menuId);
        venue.setMenu(menu);
        return venueRepository.save(venue);
    }

    private void updateOpeningHours(Venue venue, List<ScheduleDto> openHoursDto) {
        if (openHoursDto != null) {
            for (ScheduleDto scheduleDto : openHoursDto) {
                Schedule schedule = new Schedule();
                schedule.setStart(scheduleDto.getStart());
                schedule.setEnd(scheduleDto.getEnd());
                schedule.setDayOfWeek(scheduleDto.getDayOfWeek());
                schedule.setVenue(venue);
                venue.getOpenHours().add(schedule);
            }
        }
    }

    @Override
    public void delete(Long venueId) {
        final Venue venue = getById(venueId);
        venueRepository.delete(venue);
    }

    @Override
    public List<Venue> getVenues(int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size);
        return venueRepository.findAll(pageRequest).getContent();
    }

    private void assertVenueId(Long id) {
        notNull(id, "id must not be null");
    }

}
