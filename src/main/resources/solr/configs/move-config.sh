cp -r $1/* $2
curl "http://localhost:8983/solr/admin/cores?action=RELOAD&core=$1"