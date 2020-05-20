echo "Checking if hub is ready: http://$HUB_HOST:4444/wd/hub/status"

while [ "$( curl -s http://$HUB_HOST:4444/wd/hub/status | jq -r .value.ready )" != "true" ]
do
	sleep 1
done

mvn test -DSUITE=test-suite.xml -DBROWSER=$BROWSER -DGRID=true -DHUB_HOST=$HUB_HOST -DHEADLESS=$HEADLESS -DVIDEO=$VIDEO