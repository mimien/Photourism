       var marker = new google.maps.Marker();
       var map;
       var drawingManager;
       var infowindow;

       //declare map
       var map;
      function initialize() {
          var mapOptions = {
            center: new google.maps.LatLng(19.035161,-98.204744),
            zoom: 4,
          }
          map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);

//          google.maps.event.addListener(map, 'click', function(event) {
//            placeMarker(event.latLng);
//          });
//
//          var submitDiv = document.createElement('div');
//          var savePoint = new SubmitPoint(submitDiv);
//          map.controls[google.maps.ControlPosition.TOP_LEFT].push(submitDiv);

              //add the drawing tool that allows users to draw points on the map
              drawingManager = new google.maps.drawing.DrawingManager({
                  drawingMode: google.maps.drawing.OverlayType.MARKER,
                  drawingControl: true,
                  drawingControlOptions: {
                      position: google.maps.ControlPosition.TOP_CENTER,
                      drawingModes: [google.maps.drawing.OverlayType.MARKER]
                  },
                  markerOptions: {
                      // icon: new google.maps.MarkerImage('bus.png'),
                      draggable: true
                  }
              });

              //add the tools to the map
              drawingManager.setMap(map);

                  //event listener that does the following after user draws point on the map
                  google.maps.event.addListener(drawingManager, 'overlaycomplete', function (point)
                  {
                      //"clone" the save-form to put in the infowindow
                      var form =    $(".save-form").clone().show();
                      var infowindow_content = form[0];
                      infowindow = new google.maps.InfoWindow({
                          content: infowindow_content
                      });

                      //make each marker clickable
                      google.maps.event.addListener(point.overlay, 'click', function() {
                          infowindow.open(map,point.overlay);
                      });

                      //open infowindow by default
                      infowindow.open(map,point.overlay);

                        var latLng = {}
                        latLng["pt"] = point.overlay.position.lat() + " " + point.overlay.position.lng();
                        var ajax1 = {
                            data: JSON.stringify(latLng),
                            type: 'POST',
                            contentType: 'application/json; charset=utf-8',
                            async: false,
                            cache: false,
                            success: function() {
                                console.log("success");
                            },
                            error: function(a,b,c){
                              console.log("A = " + a);
                              console.log("B = " + b);
                              console.log("C = " + c);
                              console.log("error");
                            }
                        }
                    jsRoutes.controllers.MapController.savePoint().ajax(ajax1)

                  });
      }
        function SubmitPoint(controlDiv) {
          // Set CSS for the control border
          var controlUI = document.createElement('div');
          controlUI.style.backgroundColor = '#fff';
          controlUI.style.border = '1px solid #aaas';
          controlUI.style.borderRadius = '12px';
          controlUI.style.boxShadow = '0 2px 6px rgba(0,0,0,.2)';
          controlUI.style.cursor = 'pointer';
          controlUI.style.marginTop = '13px';
          controlUI.style.marginLeft = '8px';
          controlUI.style.textAlign = 'center';
          controlUI.title = 'Mark the place where the photo was taken';
          controlDiv.appendChild(controlUI);

          // Set CSS for the control interior
          var controlText = document.createElement('div');
          controlText.style.color = 'rgb(25,25,25)';
          controlText.style.fontFamily = 'Roboto,Arial,sans-serif';
          controlText.style.fontSize = '20px';
          controlText.style.lineHeight = '38px';
          controlText.style.padding = '10px';
//          controlText.style.paddingRight = '5px';
          controlText.innerHTML = 'Select';
          controlUI.appendChild(controlText);

          google.maps.event.addDomListener(controlUI, 'click', save);

        }
        function save() {
          var point = {}
          point["pt"] = marker.position.lat() + " " + marker.position.lng();

          if(marker.position != null) {
              var ajax1 = {
                  data: JSON.stringify(point),
                  type: 'POST',
                  contentType: 'application/json; charset=utf-8',
                  async: false,
                  cache: false,
                  success: function() {
                      console.log("success");
                  },
                  error: function(a,b,c){
                    console.log("A = " + a);
                    console.log("B = " + b);
                    console.log("C = " + c);
                    console.log("error");
                  }
              }
          jsRoutes.controllers.MapController.savePoint().ajax(ajax1)

          } else {
            alert("Mark the place of the photo");
          }

      }


      function placeMarker(location) {
        marker.setMap(null)
        /*
            var image = {
              url: 'http://maps.google.com/mapfiles/center.png',
              size: new google.maps.Size(20,32),
              origin: new google.maps.Point(0,0),
              anchor: new google.maps.Point(0,32)
            };
        */
              marker = new google.maps.Marker({
                  position: location,
                  map: map
        //    icon: image
              });
        map.setCenter(location);
      }

      google.maps.event.addDomListener(window, 'load', initialize);