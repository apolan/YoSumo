var map ;
  var marker1;
  var marker2;
  var marker3;
  
  var latlng = new google.maps.LatLng(4.700155,-74.05884);
  var myOptions = {
      zoom: 18,
      center: latlng,
      mapTypeId: google.maps.MapTypeId.ROADMAP
  };



  function placeMarker(marker,location) {
    marker = new google.maps.Marker
	({
        position: location, 
        map: map
   	});

    
  }
  
  
  
  
function newmarker(lat, log, titulo, comentario ){
   if(map !== null ){
    map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
   }

	console.log(lat,log,titulo,comentario);
	var marker = new google.maps.Marker({
    position: new google.maps.LatLng(Number(lat),Number(log)),
    title: "a",
	label: "a"
    });

    marker.setMap(map);

}

