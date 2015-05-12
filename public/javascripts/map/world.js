var map;
var points = [];
    function initialize() {
      var myLatlng = new google.maps.LatLng(37.4419, -122.1419);
      var mapOptions = {
        zoom: 3,
        center: myLatlng
      }
      map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);
        getData();
    }

        function getData() {
            var ajax1 = {
                processData:false,
                type: 'GET',
                beforeSend:function(jqXHR, settings){
                    jqXHR.setRequestHeader("Content-Type", "application/json");
                },
                success: function(data, textStatus, jqXHR){
                    var latLng;
                    for(i = 0; i < data.length; i++) {
                        latLng = data[i].split(" ");
                        console.log(latLng[0] + " " + latLng[1]);
                        var location = new google.maps.LatLng(latLng[0], latLng[1]);
                        var marker= new google.maps.Marker({
                                  position: location,
                        });
                        points.push(marker);
                    }
                    console.log(points.length);
                    var markerCluster = new MarkerClusterer(map, points);
                   },
                error: function(jqXHR, textStatus, errorThrown){
                }
           }
        jsRoutes.controllers.PhotosController.all().ajax(ajax1)
        }
      google.maps.event.addDomListener(window, 'load', initialize);