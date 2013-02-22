if [ $# -ne 1 ]; then
    echo "Usage: $0 create|load|clean|dump"
fi

case $1 in
    create)
        psql -h alcor.inf.unibz.it -U ds_group2 -f create.sql ds_group2
    ;;
    load)
        psql -h alcor.inf.unibz.it -U ds_group2 -f load.sql ds_group2
    ;;
    clean)
        psql -h alcor.inf.unibz.it -U ds_group2 -f clean.sql ds_group2
    ;;
    dump)
        pg_dump --column-inserts --data-only -U ds_group2 -h alcor.inf.unibz.it ds_group2 | grep INSERT
    ;;
    reload)
        echo "not implemented yet!"
    ;;
esac


exit 0