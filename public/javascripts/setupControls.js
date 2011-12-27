

$(document).ready(function() {
	$("#loading").hide();
	
	$("#loading").ajaxStart(function(){
	   $(this).show();
	});

	$("#loading").ajaxStop(function(){
      $(this).hide();
	});

	$(function() {
	    oTable = $('#content #dataTable').dataTable({
	        "bJQueryUI": true,
	        "bLengthChange": false,
	        "bFilter": false,
	        "bPaginate": true,
	        "bInfo": true,
	        "bSort": true,
	    });
	});

	$(function() {
		$( "#tabs" ).tabs({
		});
	});

	$(function() {
		$("input[type='text'],input[type='autocomplete'],input[type='password'],input[type='date'],input[type='datetime'],input[type='number']").addClass("ui-inputfield ui-inputtext ui-widget ui-corner-all");
	});

	$(function() {
		$("input[type='submit'], input[type='checkbox']").button();
	});

	$(function() {
		$(".iconbutton").button({
			icons: {
				primary: "ui-icon-gear"
			},
			text: true
		});
	});
	$(function() {
		$("input[type='date']").datepicker();
	});

	$(function() {
		$("input[type='datetime']").datetimepicker({
			ampm: true
		});
	});

	$(function() {
		$( ".buttonBox a" ).button();
	});

	$(function() {
		$('select').selectmenu({width: 200});
	});

	$(function() {
		$('#switcher').themeswitcher();
	});
	
	$(function() {
		$( "#radioButtonSet" ).buttonset();
	});
	
	$(function() {
		$( ".accordian" ).accordion();
	});

	$(function() {
		$( ".collapsible-accordian" ).accordion({
			collapsible: true
		});
	});

	$(function() {
		var availableTags = [
			"ActionScript",
			"AppleScript",
			"Asp",
			"BASIC",
			"C",
			"C++",
			"Clojure",
			"COBOL",
			"ColdFusion",
			"Erlang",
			"Fortran",
			"Groovy",
			"Haskell",
			"Java",
			"JavaScript",
			"Lisp",
			"Perl",
			"PHP",
			"Python",
			"Ruby",
			"Scala",
			"Scheme"
		];
		$( "input[type='autocomplete']" ).autocomplete({
			source: availableTags
		});
	});
	
	tinymce.init({
        mode:'textareas',
        theme:'advanced'
    });
});

function loadContent(object, url) {
	object.load(url);
};

