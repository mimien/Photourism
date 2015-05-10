    var map;
    var markers = [];

    function initialize() {
      var mapOptions = {
            center: new google.maps.LatLng(19.035161,-98.204744),
            zoom: 3,
            minZoom: 2,
            scrollwheel: false,
      }
      map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);

      google.maps.event.addListener(map, 'click', function(event) {
        getData(event.latLng);
      });
    }

    function getData() {
      var contador = 0;

      var ajax1 = {
          cache: false,
          success: function(json,otro,c) {
            $.each(json, function(index,object){
              console.log(object);
              var coord = object.split(",");
              console.log(coord);
              var marker1 = [];
              for(i = 0; i < coord.length; i++) {
                var location = coord[i].split(" ");
                var marker = new google.maps.LatLng(location[0], location[1]);
                marker1[i] = marker;
              }
              markers[contador] = marker1;
              contador++;
             //placeMarker(new google.maps.LatLng(coord[0], coord[1]));

             var poligs = [];

            for(i = 0; i < markers.length; i++) {
               poligs[i] = new google.maps.Polygon({
                  paths: markers[i],
                  strokeColor: '#0066FF',
                  strokeOpacity: 0.8,
                  strokeWeight: 2,
                  fillColor: '#0066FF',
                  fillOpacity: 0.35
               });
               poligs[i].setMap(map);
             }
           });
            console.log("success");
          },
          error: function(a,b,c){
            console.log("A = " + a);
            console.log("B = " + b);
            console.log("C = " + c);
            console.log("error");
          }
      }
       //var locationsJson = JSON.stringify(locations);
      jsRoutes.controllers.MapController.index().ajax(ajax1)
    }

    function placeMarker(location) {
      var marker = new google.maps.Marker({
        position: location,
        map: map
      });
    }

    $(document).ready(function(){
      getData();
    });

    google.maps.event.addDomListener(window, 'load', initialize);