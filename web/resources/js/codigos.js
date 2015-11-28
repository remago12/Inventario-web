function getTime(){
	var meses = new Array ("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre");
	var f=new Date();
	document.write(f.getDate() + " de " + meses[f.getMonth()] + " de " + f.getFullYear());
}

function txtFocus(){
    $('#IDFormCreacionVariables\\:IDInptTxtFormulaIngresada').css('height','400px');
    $('#IDFormCreacionVariables\\:IDPanelGridFormulario').hide(666);
    $('#IDFormCreacionVariables\\:IDCmdBtnMin').show();
    $('#IDFormCreacionVariables\\:IDCmdBtnMax').hide();
    ocultarAyuda();
}

function txtBlur(){
    $('#IDFormCreacionVariables\\:IDInptTxtFormulaIngresada').css('height','100px');
    $('#IDFormCreacionVariables\\:IDPanelGridFormulario').show(666);
    $('#IDFormCreacionVariables\\:IDCmdBtnMax').show();
    $('#IDFormCreacionVariables\\:IDCmdBtnMin').hide();
    ocultarAyuda();
    ocultarTodaAyuda();
}

function mostrarAyuda(){
	 /*$('#IDContenedorAyuda').show(666);IDFormAyudaEditor
	 $('#IDContenedorAyuda\\:IDFormAyudaEditor').show(666);*/
	 $('#IDFormAyudaEditor').show(666);
	 $('#IDFormCreacionVariables\\:IDCmdBtnMAyuda').hide();
	 $('#IDFormCreacionVariables\\:IDCmdBtnOAyuda').show();
}

function ocultarAyuda(){
	/* $('#IDContenedorAyuda').hide(666);
    $('#IDContenedorAyuda\\:IDFormAyudaEditor').hide(666); */
    $('#IDFormAyudaEditor').hide(666);
    $('#IDFormCreacionVariables\\:IDCmdBtnOAyuda').hide();
    $('#IDFormCreacionVariables\\:IDCmdBtnMAyuda').show();
}

function ocultarTodaAyuda(){
	$('#IDFormCreacionVariables\\:IDCmdBtnOAyuda').hide();
    $('#IDFormCreacionVariables\\:IDCmdBtnMAyuda').hide();
}

var timeoutID;

function delayedtxtBlur() {
  timeoutID = window.setTimeout(txtBlur, 2000);
}


function clearAlert() {
  window.clearTimeout(timeoutID);
}


function isNumber(event) {
  if (event) {
    var charCode = (event.which) ? event.which : event.keyCode;
    if (charCode < 48 || charCode > 57) 
       return false;
  }
  return true;
}