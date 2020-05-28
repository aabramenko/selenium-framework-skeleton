echo "Checking if hub is ready: http://$HUB_HOST:4444/wd/hub/status"

int n = 0
while [ "$( curl -s http://$HUB_HOST:4444/wd/hub/status | jq -r .value.ready )" != "true" ] && [ $n -lt 15 ]
do
  echo "$( curl -s http://$HUB_HOST:4444/wd/hub/status | jq -r .value.ready )"
	sleep 1
	n=$n+1
	echo $n
done

mvn test -DSUITE=$SUITE \
        -DBROWSER=$BROWSER \
        -DGRID=true \
        -DHUB_HOST=$HUB_HOST \
        -DHEADLESS=$HEADLESS \
        -DVIDEO=$VIDEO