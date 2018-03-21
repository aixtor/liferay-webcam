<%@ include file="init.jsp" %>
<portlet:actionURL var="registrationURL" name="registerDTIUser">
</portlet:actionURL>
<portlet:resourceURL var="openWebCamURL">
	<portlet:param name="action" value="open-webcam" /> 
</portlet:resourceURL>
<style>
	#results {background:#ccc; }
</style>
<div class="container">
	<div class="form-container">
		<aui:form name="registrationFm" method="POST" action="${registrationURL}">
			<div class="registration">
				<div class="row">
					<div class="col-md-6">
							<aui:input name="imageData" type="hidden" />
							<aui:input name="firstName" value=" " showRequiredLabel="" label="first-name" cssClass="" maxlength="50" minlength="3">
								<aui:validator name="required" />
								<aui:validator name="custom" errorMessage="first-name-validation-msg">
									function validateFirstName(name){ 
										if(name.trim() == ''){
											return true;
										}
										var re = /^([a-zA-Z ']{1,75})$/;
										return re.test(name);
									}
								</aui:validator>
							</aui:input>
							<aui:input name="middleName" value=" " showRequiredLabel="" label="middle-name" maxlength="50" minlength="1"></aui:input>
							<aui:input name="lastName" value=" " showRequiredLabel="" label="last-name" maxlength="50" minlength="3">
								<aui:validator name="required" />
								<aui:validator name="custom" errorMessage="last-name-validation-msg">
									function validateLastName(name){ 
										if(name.trim() == ''){
											return true;
										}
										var re = /^([a-zA-Z ']{1,75})$/;
										return re.test(name);
									}
								</aui:validator>
							</aui:input>
							<aui:input name="email" value='' showRequiredLabel="" id="email" label="email-id" maxlength="75">
								<aui:validator name="required" />
								<aui:validator name="custom" errorMessage="email-validation-msg">
									function validateEmailAddress(email){
										if(email.trim() == ''){
											return true;
										}
										var re = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
										return re.test(email);
									}
								</aui:validator>
							</aui:input>
							<aui:input name="mobileNo" value="" showRequiredLabel="" label="mobile-number" maxlength="10">
								<aui:validator name="required" />
								<aui:validator name="custom" errorMessage="mobile-number-validation-msg">
									function validateMobileNumber(mobileNo){
										if(mobileNo.trim() == ''){
											return true;
										}
				  							var re = /^([0-9]{10})$/;
				  							return re.test(mobileNo);
									}
								</aui:validator>
							</aui:input>
							<div class="submitbtn">
								<aui:button type="submit" value="submit" name="submitBtn" />
							</div>
					</div>
					<div class="col-md-6">
						<div id="dtiCamera"></div>
						<div id="<portlet:namespace />webCamButtonContainer">
							<aui:button value="take-picture" onClick="javascript:takeSnapshot();" />
						</div>
						<div id="<portlet:namespace />cameraResults">Your captured image will appear here...</div>
					</div>
				</div>
			</div>
		</aui:form>
	</div>
</div>
<script type="text/javascript">

takeSnapshot = function(){
	// take snapshot and get image data
	Webcam.snap( function(data_uri) {
		$('#<portlet:namespace />imageData').val(data_uri);
		var cameraResultsHtml = '<h2>Here is your image:</h2>' + 
			'<img src="'+data_uri+'"/>';
		$('#<portlet:namespace />cameraResults').html(cameraResultsHtml);
	} );
};

//intialize Webcam
Webcam.set({
	width: 320,
	height: 240,
	image_format: 'jpeg',
	jpeg_quality: 90
});
Webcam.attach('#dtiCamera');
</script>