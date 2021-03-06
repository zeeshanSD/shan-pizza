<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Social Utility</title>

<!-- Bootstrap -->

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"
	integrity="sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ=="
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css"
	integrity="sha384-aUGj/X2zp5rLCbBxumKTCw2Z50WgIr1vs/PFN4praOTvYXWlVyh2UtNUU0KAUhAX"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/css/bootstrap-datetimepicker.min.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.7.5/css/bootstrap-select.min.css" />

<link rel="stylesheet" href="css/nav.css" />
<link rel="stylesheet"
	href="css/custom-theme/jquery-ui-1.9.2.custom.css" />
<!-- JS -->
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"
	integrity="sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ=="
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.6/moment.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.7.5/js/bootstrap-select.min.js"></script>

<script type="text/javascript">

	$(document).ready(
			function() {

				$('#publish').click(
						function() {
							

							var env = $('#env').val();
							var message = $('#message').val();

							if (env == "") {
						        var mymodal = $('#alertModal');
								var text = "<p>Please select social media platform</p>";

								mymodal.find('.modal-body')
										.html(text);
								$('#alertModal').modal('show');
								
							} 
							else if(message == "") {
								 var mymodal = $('#alertModal');
									var text = "<p>Message field can't be empty</p>";

									mymodal.find('.modal-body')
											.html(text);
									$('#alertModal').modal('show');
							}
							else {
								
								$('#form-pannel').hide();
								$('#processing-pannel').show();
								$('#result').hide();
								$('#spinner')
								.show();
								
								$
								.ajax({
									type : "POST",
									url : "/shan-pizza/rest/social/publish",
									data : message,
									success : function(
											data) {
										tmp = data;
										$('#result')
												.html(
														"<h3><span class=\"label label-success\">Success!</span></h3>");
										$('#result').show();
										$(
												'#form-pannel')
												.show();
										
										$(
												'#processing-pannel')
												.hide();
										$('#spinner')
												.hide();

									},
									error : function(
											request,
											status,
											error) {
										console.log(request.status);
										if (request.status == 404) {
											var errorMsg = 'The requested resource not found. [404]';
										} else if (request.status == 500) {
											var errorMsg = 'Internal Server Error [500].';
										} else {
											var errorMsg = request.responseText;
										}
										
										var mymodal = $('#errorModal');
										var text = "<p>Your request cannot be processed at this time due to errors</p>\
												<p class=\"text-danger\"><large><B>Error: "
												+ errorMsg
												+ "</B></large></p>";
										mymodal
												.find(
														'.modal-body')
												.html(
														text);
										$("#errorModal")
												.modal(
														'show');
										$('#result')
												.html(
														"<h3><span class=\"label label-danger\">Error!</span></h3>"
																+ errorMsg);
										$('#result')
												.show();
										$(
												'#form-pannel')
												.show();
										$('#message-publish').hide();
										$(
												'#processing-pannel')
												.hide();
										$('#spinner')
												.hide(); }
										
									});
							
						}});

			});
</script>

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<div class="container">

		<jsp:include page="WEB-INF/includes/nav.jsp"></jsp:include>
		
		<!-- Main component for a primary marketing message or call to action -->
		<div class="jumbotron"
			style="background-image: url('pizza.png'); background-size: cover;">

			<h2>Publish Pizza Specials</h2>
			<p>
				<select id="env" class="selectpicker">
					<option value="">Select Social Media Platform</option>
					<option value="twitter">Twitter</option>
				</select>
			</p>
			<p>
				<label for="comment">Message:</label>
				<textarea class="form-control" rows="6" id="message"
					style="max-width: 50%"></textarea>
			</p>

			<div id="form-pannel">

				<p>
					<a class="btn btn-lg btn-primary" href="javascript:;" role="button"
						id="publish">Publish</a>
				</p>
			</div>

			<div id="processing-pannel" style="display: none;">
				<div id="spinner" style="display: none;">
					<strong>A request to publish has already been made. Please
						wait till it is complete. </strong>
				</div>
				<img alt="Please Wait" src="images/spinner.gif" />

			</div>
			<div id="result" style="display: none;"></div>
		</div>

</div>

	<div id="alertModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">Alert</h4>
				</div>
				<div class="modal-body"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">OK</button>
				</div>
			</div>
		</div>
	</div>

	<div id="errorModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">Request Failed</h4>
				</div>
				<div class="modal-body"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">OK</button>
				</div>
			</div>
		</div>
	</div>
	<!-- /container -->
</body>
</html>