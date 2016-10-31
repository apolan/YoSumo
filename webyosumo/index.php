

<html>
	<head>
		<meta charset="utf-8"/>
		<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
		<meta name="viewport" content="width=device-width, initial-scale=1"/>
		<link href="css/bootstrap.min.css" rel="stylesheet"/>
		<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAZEyP0KHfNWOyox9gfU5jePbSFDyNdzjg"></script>
		<script type="text/javascript" src="js/WebYosumo.js"></script>
		<script language="javascript" type="text/javascript" src="js/jquery.js"></script>
		<title>Yo sumo</title>
		<script id="source" language="javascript" type="text/javascript">
		  $(function () 
  {
    //-----------------------------------------------------------------------
    // 2) Send a http request with AJAX http://api.jquery.com/jQuery.ajax/
    //-----------------------------------------------------------------------
    $.ajax({                                      
      url: 'php/connect.php',                  //the script to call to get data          
      data: "",                        //you can insert url argumnets here to pass to api.php
                                       //for example "id=5&parent=6"
      dataType: 'json',                //data format      
      success: function(data)          //on recieve of reply
      {
        var nombre     = data[2];              //get id
        var comentario = data[4];              //get id
        var lat        = data[5];              //get id
        var log        = data[6];              //get id
        var fecha      = data[7];              //get id
        //--------------------------------------------------------------------
        // 3) Update html content
        //--------------------------------------------------------------------
       
       newmarker(lat,log,nombre, comentario );
        //recommend reading up on jquery selectors they are awesome 
        // http://api.jquery.com/category/selectors/
      } 
    });
  }); 
  </script>
	</head>

<body >
			<header role="banner" class="navbar navbar-fixed-top navbar-inverse">
			<div class="container">
				<div class="navbar-inverse side-collapse in">
					<nav role="navigation" class="navbar-collapse">
						<ul class="nav navbar-nav">
							<li><img src="img/yosumo.png" alt="yo sumo" height="42" width="42"></li>
							<li><a href="#Home">Yosumo</a></li>
							<li><a href="#users">Denuncias de hoy</a></li>
							<li><a href="">Denuncias total</a></li>
						</ul>
					</nav>
				</div>
			</div>
		</header>
		<div class="container">
			
		</div>
		
		<div id="map_canvas" style="width:100%; height:100%"></div>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
	</body>
</html>