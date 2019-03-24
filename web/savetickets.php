<?php 
header("Access-Control-Allow-Origin: *");
include_once 'db_conn.php';
 
$_SESSION['pin'] = $_POST['pin'];
$pin = $_POST['pin']; 

$database = new Database();
$db = $database->getConnection();
$query = "SELECT eth_address, smartcontract FROM ouchsu00_savetickets.access WHERE pin=".$pin;

/*
foreach ($db->query($query) as $row) {
    $result['eth_address'] = $row['eth_address'];
	$result['smartcontract'] = $row['smartcontract;
} */

$query = "SELECT ticket_id, eth_address, smartcontract FROM ouchsu00_savetickets.access WHERE pin=".$pin;
$tickets = array();
    
    


$url = "http://api.cultserv.ru/v4/subevents/get/?session=123&id=661584";
$url1 = "http://api.cultserv.ru/v4/subevents/get/?session=123&id=666560";
$url2 = "http://api.cultserv.ru/v4/subevents/get/?session=123&id=662815";
//$data = array('session' => '123', 'id' => '661584');
$options = array(
  'http' => array(
    'method'  => 'GET',
    //'content' => json_encode( $data ),
    'header'=>  "Content-Type: application/json\r\n" .
                "Accept: application/json\r\n"
    )
);
$context  = stream_context_create( $options );
$result = file_get_contents( $url, false, $context );
$event_data = json_decode($result);

$result = file_get_contents( $url1, false, $context );
$mock_data1 = json_decode($result);
$result = file_get_contents( $url2, false, $context );
$mock_data2 = json_decode($result);



?>
<!doctype html>
<html lang="en">

<head>
  <title>SaveTickets</title>
  <!-- Required meta tags -->
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <!--     Fonts and icons     -->
  <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Roboto+Slab:400,700|Material+Icons" />
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css">
  <!-- Material Kit CSS -->
  <link href="assets/css/material-dashboard.css?v=2.1.0" rel="stylesheet" />
  
  <script src="https://cdn.jsdelivr.net/gh/ethereum/web3.js/dist/web3.min.js"></script>
  <script src="assets/js/config.js"></script>
  <script src="assets/js/smartcontract.js"></script>
  <script src="http://code.jquery.com/jquery-2.0.2.min.js"></script>
  <script>

        if (typeof web3 !== 'undefined') {
            web3 = new Web3(web3.currentProvider);
        } else {
            // set the provider you want from Web3.providers
            alert('Please activate MetaMask Plugin and reload page! You can find it here: https://metamask.io/')
        }
		
		function addTicket() {
          let ticketName =  "<?php  echo $event_data->message->title; ?>";
          let ticketPrice = 1000;
		  let ticketOwner = "0x2A2233dFF9e7f8E6090aDB5Ea6d06723E1f62BFC"; 
		  let ticketPersonInfo = "Ivan"; 
		  let ticketPlace = "<?php echo $event_data->message->venue->title; ?>";
		  let ticketId = "661584";
		  let ticketDate = "<?php  echo $event_data->message->date; ?> ";
		  let ticketAll = "<?php  echo $event_data->message->title; echo "#"; $event_data->message->venue->title; echo "#"; echo $event_data->message->date; echo "#"; echo "661584"; ?>"
		  let user_pin = "<?php  echo $pin; ?>";
          deployConract(ticketName, ticketPrice, ticketOwner, ticketPersonInfo, ticketPlace, ticketId, ticketDate, ticketAll, user_pin);
        }
		
		function track(){
          let addr = "0x2c264db63f4b96e2880a1aa176c601f3d9b0e126";
          let contract = openContract(addr);
          getStates(contract, addr);
        }
		
		function delegate_ticket() {
			let addr = "0x71a5cd0ac4e89f1e7829de96d2ec163bfe5d2932";
			let contract = openContract(addr);
			delegate (contract, addr);
		}

        function open_t() {
			let addr = "0x2c264db63f4b96e2880a1aa176c601f3d9b0e126";
			let contract = openContract(addr);
			open_ticket (contract, addr);
		}
  
</script>
  <!--
  <form id="create_contract_content" action="">
    
        <input class = "input" id="productName" placeholder="product">
        <input class="button" id="my_button" type="button" onclick="addProduct()" value="Add to AnchorChain"><br />
    </form>
  <script src="assets/js/smartcontract.js"></script>-->
    
</head>

<body class="dark-edition">
  <div class="wrapper ">
    <div class="sidebar" data-color="purple" data-background-color="black" data-image="./assets/img/sidebar-2.jpg">
      <!--
      Tip 1: You can change the color of the sidebar using: data-color="purple | azure | green | orange | danger"

      Tip 2: you can also add an image using data-image tag
  -->
      <div class="logo">
        <a href="#" class="simple-text logo-normal">
          Save Tickets
        </a>
      </div>
      <div class="sidebar-wrapper">
        <ul class="nav">
          <li class="nav-item active  ">
            <a class="nav-link" href="javascript:void(0)">
              <i class="material-icons">mail</i>
              <p>Мои билеты</p>
            </a>
          </li>
		  <li class="nav-item active  ">
            <a class="nav-link" href="opentickets.php">
              <i class="material-icons">drafts</i>
              <p>Распечатанные билеты</p>
            </a>
          </li>
		  <li class="nav-item active  ">
            <a class="nav-link" href="javascript:void(0)">
              <i class="material-icons">list_alt</i>
              <p>История</p>
            </a>
          </li>
		  <li class="nav-item active  ">
            <a class="nav-link" href="authorization.php">
              <i class="material-icons">person</i>
              <p>Выход</p>
            </a>
          </li>
          <!-- your sidebar here -->
        </ul>
      </div>
    </div>
    <div class="main-panel">
      <!-- Navbar -->
      <nav class="navbar navbar-expand-lg navbar-transparent navbar-absolute fixed-top ">
        <div class="container-fluid">
          <div class="navbar-wrapper">
            <a class="navbar-brand" href="javascript:void(0)">Мои билеты</a>
          </div>
          <button class="navbar-toggler" type="button" data-toggle="collapse" aria-controls="navigation-index" aria-expanded="false" aria-label="Toggle navigation">
            <span class="sr-only">Toggle navigation</span>
            <span class="navbar-toggler-icon icon-bar"></span>
            <span class="navbar-toggler-icon icon-bar"></span>
            <span class="navbar-toggler-icon icon-bar"></span>
          </button>
          <div class="collapse navbar-collapse justify-content-end">
            <ul class="navbar-nav">
              <li class="nav-item">
                <a class="nav-link" href="javascript:void(0)">
                  <i class="material-icons">monetization_on</i>
                  <p class="d-lg-none d-md-block">
                    Кошелек
                  </p>
                </a>
              </li>
              <!-- your navbar here -->
            </ul>
          </div>
        </div>
      </nav>
      <!-- End Navbar -->
      <div class="content">
        <div class="container-fluid">
          <div class="row">
		  
		  <?php foreach ($db->query($query) as $row) {
            $sm_ticket_id = $row['ticket_id'];
			$sm_eth_addr = $row['eth_address'];
			$sm_smartcontract = $row['smartcontract'];
			
			$url = "http://api.cultserv.ru/v4/subevents/get/?session=123&id=".$sm_ticket_id;

//$data = array('session' => '123', 'id' => '661584');
            $options = array(
           'http' => array(
    'method'  => 'GET',
    //'content' => json_encode( $data ),
    'header'=>  "Content-Type: application/json\r\n" .
                "Accept: application/json\r\n"
    )
);
$context  = stream_context_create( $options );
$result = file_get_contents( $url, false, $context );
$event_data = json_decode($result);
			
    
      ?>
   
		  
            <div class="col-xl-4 col-lg-12">
              <div class="card card-chart">
                <div class="card-header card-header-success">
                  <div class="" id=""><img style="max-width:100%;" src="http://media.cultserv.ru/i/1200x800/<?php echo $event_data->message->image;?>"></div>
                </div>
                <div class="card-body">
                  <h4 class="card-title"><?php  echo $event_data->message->title; ?></h4>
                  <p class="card-category"><?php echo $event_data->message->venue->title; ?></p>
                </div>
                <div class="card-footer">
                  <div class="stats">
                    <i class="material-icons">access_time</i><?php  echo $event_data->message->date; ?> 
                  </div>
                </div>
				<!--<button onclick="addTicket();">оооо</button>
				<button onclick="track();">llll</button>-->
				<button id="<?php  echo $sm_smartcontract; ?>" class="btn btn-primary btn-round" onclick="delegate_ticket();">Делегировать</button>
				<a href="#open" class="btn btn-primary btn-round">Посмотреть</a>
				<button id="<?php  echo $sm_smartcontract; ?>" class="btn btn-primary btn-round" onclick="open_t();">Открыть</button>
				<a href="#back" class="btn btn-primary btn-round">Вернуть</a>	
              </div>
            </div>
			
			<?php  } ?>
			<!--<div class="col-xl-4 col-lg-12">
              <div class="card card-chart">
                <div class="card-header card-header-success">
                  <div class="ct-chart" id=""><img style="max-width:100%;" src="http://media.cultserv.ru/i/1200x800/<?php echo $mock_data1->message->image;?>"></div>
                </div>
                <div class="card-body">
                  <h4 class="card-title"><?php  echo $mock_data1->message->title; ?></h4>
                  <p class="card-category"><?php echo $mock_data1->message->venue->title; ?></p>
                </div>
                <div class="card-footer">
                  <div class="stats">
                    <i class="material-icons">access_time</i><?php  echo $mock_data1->message->date; ?> 
                  </div>
                </div>
				<a href="#delegate" class="btn btn-primary btn-round">Делегировать</a>
				<a href="#open" class="btn btn-primary btn-round">Посмотреть</a>
				<a href="#open" class="btn btn-primary btn-round">Открыть</a>
				<a href="#back" class="btn btn-primary btn-round">Вернуть</a>
              </div>
            </div>
			<div class="col-xl-4 col-lg-12">
              <div class="card card-chart">
                <div class="card-header card-header-success">
                  <div class="ct-chart" id=""><img style="max-width:100%;" src="http://media.cultserv.ru/i/1200x800/<?php echo $mock_data2->message->image;?>"></div>
                </div>
                <div class="card-body">
                  <h4 class="card-title"><?php  echo $mock_data2->message->title; ?></h4>
                  <p class="card-category"><?php echo $mock_data2->message->venue->title; ?></p>
                </div>
                <div class="card-footer">
                  <div class="stats">
                    <i class="material-icons">access_time</i><?php  echo $mock_data2->message->date; ?> 
                  </div>
                </div>
				<a href="#delegate" class="btn btn-primary btn-round">Делегировать</a>
				<a href="#open" class="btn btn-primary btn-round">Посмотреть</a>
				<a href="#open" class="btn btn-primary btn-round">Открыть</a>
				<a href="#back" class="btn btn-primary btn-round">Вернуть</a>
              </div>
            </div>
			
			<!-- for unsave --
            <div class="col-xl-4 col-lg-12">
              <div class="card card-chart">
                <div class="card-header card-header-danger">
                  <div class="ct-chart" id="completedTasksChart"></div>
                </div>
                <div class="card-body">
                  <h4 class="card-title">Completed Tasks</h4>
                  <p class="card-category">Last Campaign Performance</p>
                </div>
                <div class="card-footer">
                  <div class="stats">
                    <i class="material-icons">access_time</i> campaign sent 2 days ago
                  </div>
                </div>
              </div>
            </div> -->
					
			
          </div>
        </div>
      </div>
      <footer class="footer">
        <div class="container-fluid">
          <nav class="float-left">
            <ul>
              <li>
                <a href="#">
                  StartBlock
                </a>
              </li>
            </ul>
          </nav>
          <div class="copyright float-right">
            &copy;
            <script>
              document.write(new Date().getFullYear())
            </script>, made with <i class="material-icons">favorite</i> by
            <a href="https://www.startblock.online" target="_blank">StartBlock</a>.
          </div>
          <!-- your footer here -->
        </div>
      </footer>
    </div>
  </div>
  <!--   Core JS Files   -->
  <script src="./assets/js/core/jquery.min.js"></script>
  <script src="./assets/js/core/popper.min.js"></script>
  <script src="./assets/js/core/bootstrap-material-design.min.js"></script>
  <script src="https://unpkg.com/default-passive-events"></script>
  <script src="./assets/js/plugins/perfect-scrollbar.jquery.min.js"></script>
  <!-- Place this tag in your head or just before your close body tag. -->
  <script async defer src="https://buttons.github.io/buttons.js"></script>
  <!--  Google Maps Plugin    -->
  <script src="https://maps.googleapis.com/maps/api/js?key=YOUR_KEY_HERE"></script>
  <!-- Chartist JS -->
  <script src="./assets/js/plugins/chartist.min.js"></script>
  <!--  Notifications Plugin    -->
  <script src="./assets/js/plugins/bootstrap-notify.js"></script>
  <!-- Control Center for Material Dashboard: parallax effects, scripts for the example pages etc -->
  <script src="./assets/js/material-dashboard.js?v=2.1.0"></script>
  <!-- Material Dashboard DEMO methods, don't include it in your project! -->
  <script src="./assets/demo/demo.js"></script>
  <script>
    $(document).ready(function() {
      $().ready(function() {
        $sidebar = $('.sidebar');

        $sidebar_img_container = $sidebar.find('.sidebar-background');

        $full_page = $('.full-page');

        $sidebar_responsive = $('body > .navbar-collapse');

        window_width = $(window).width();
         
		addTicket(); 
		 
        $('.fixed-plugin a').click(function(event) {
          // Alex if we click on switch, stop propagation of the event, so the dropdown will not be hide, otherwise we set the  section active
          if ($(this).hasClass('switch-trigger')) {
            if (event.stopPropagation) {
              event.stopPropagation();
            } else if (window.event) {
              window.event.cancelBubble = true;
            }
          }
        });

        $('.fixed-plugin .active-color span').click(function() {
          $full_page_background = $('.full-page-background');

          $(this).siblings().removeClass('active');
          $(this).addClass('active');

          var new_color = $(this).data('color');

          if ($sidebar.length != 0) {
            $sidebar.attr('data-color', new_color);
          }

          if ($full_page.length != 0) {
            $full_page.attr('filter-color', new_color);
          }

          if ($sidebar_responsive.length != 0) {
            $sidebar_responsive.attr('data-color', new_color);
          }
        });

        $('.fixed-plugin .background-color .badge').click(function() {
          $(this).siblings().removeClass('active');
          $(this).addClass('active');

          var new_color = $(this).data('background-color');

          if ($sidebar.length != 0) {
            $sidebar.attr('data-background-color', new_color);
          }
        });

        $('.fixed-plugin .img-holder').click(function() {
          $full_page_background = $('.full-page-background');

          $(this).parent('li').siblings().removeClass('active');
          $(this).parent('li').addClass('active');


          var new_image = $(this).find("img").attr('src');

          if ($sidebar_img_container.length != 0 && $('.switch-sidebar-image input:checked').length != 0) {
            $sidebar_img_container.fadeOut('fast', function() {
              $sidebar_img_container.css('background-image', 'url("' + new_image + '")');
              $sidebar_img_container.fadeIn('fast');
            });
          }

          if ($full_page_background.length != 0 && $('.switch-sidebar-image input:checked').length != 0) {
            var new_image_full_page = $('.fixed-plugin li.active .img-holder').find('img').data('src');

            $full_page_background.fadeOut('fast', function() {
              $full_page_background.css('background-image', 'url("' + new_image_full_page + '")');
              $full_page_background.fadeIn('fast');
            });
          }

          if ($('.switch-sidebar-image input:checked').length == 0) {
            var new_image = $('.fixed-plugin li.active .img-holder').find("img").attr('src');
            var new_image_full_page = $('.fixed-plugin li.active .img-holder').find('img').data('src');

            $sidebar_img_container.css('background-image', 'url("' + new_image + '")');
            $full_page_background.css('background-image', 'url("' + new_image_full_page + '")');
          }

          if ($sidebar_responsive.length != 0) {
            $sidebar_responsive.css('background-image', 'url("' + new_image + '")');
          }
        });

        $('.switch-sidebar-image input').change(function() {
          $full_page_background = $('.full-page-background');

          $input = $(this);

          if ($input.is(':checked')) {
            if ($sidebar_img_container.length != 0) {
              $sidebar_img_container.fadeIn('fast');
              $sidebar.attr('data-image', '#');
            }

            if ($full_page_background.length != 0) {
              $full_page_background.fadeIn('fast');
              $full_page.attr('data-image', '#');
            }

            background_image = true;
          } else {
            if ($sidebar_img_container.length != 0) {
              $sidebar.removeAttr('data-image');
              $sidebar_img_container.fadeOut('fast');
            }

            if ($full_page_background.length != 0) {
              $full_page.removeAttr('data-image', '#');
              $full_page_background.fadeOut('fast');
            }

            background_image = false;
          }
        });

        $('.switch-sidebar-mini input').change(function() {
          $body = $('body');

          $input = $(this);

          if (md.misc.sidebar_mini_active == true) {
            $('body').removeClass('sidebar-mini');
            md.misc.sidebar_mini_active = false;

            $('.sidebar .sidebar-wrapper, .main-panel').perfectScrollbar();

          } else {

            $('.sidebar .sidebar-wrapper, .main-panel').perfectScrollbar('destroy');

            setTimeout(function() {
              $('body').addClass('sidebar-mini');

              md.misc.sidebar_mini_active = true;
            }, 300);
          }

          // we simulate the window Resize so the charts will get updated in realtime.
          var simulateWindowResize = setInterval(function() {
            window.dispatchEvent(new Event('resize'));
          }, 180);

          // we stop the simulation of Window Resize after the animations are completed
          setTimeout(function() {
            clearInterval(simulateWindowResize);
          }, 1000);

        });		
      });

    });
  </script>
  

</body>

</html>