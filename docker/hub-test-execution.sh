while [ "$( curl -s http://$HUB_HOST:4444/wd/hub/status | jq -r .value.ready )" != "true" ]
do
  echo "."
  echo "Checking if hub is ready: http://$HUB_HOST:4444/wd/hub/status"
  echo "$( curl -s http://$HUB_HOST:4444/wd/hub/status | jq -r .value.ready )"
	sleep 1
done

mvn test -DSUITE=$SUITE \
        -DBROWSER=$BROWSER \
        -DGRID=true \
        -DHUB_HOST=$HUB_HOST \
        -DHEADLESS=$HEADLESS \
        -DVIDEO=$VIDEO