$("#introduced").datepicker({
	dateFormat : messages["form.date.format"],
	changeMonth : true,
	changeYear : true,
	minDate : new Date(1970, 1 - 1, 1)
});
$("#discontinued").datepicker({
	dateFormat : messages["form.date.format"],
	changeMonth : true,
	changeYear : true
});

$('#editComputer').validate({
	rules : {
		name : {
			required : true,
			minlength : 2
		}
	},
	messages : {
		name : {
			required : messages["form.error.nameNeeded"],
			minlength : messages["form.error.nameLength"]
		}
	}
});

(function($) {
	$.fn.changeLanguage = function(lang) {
		var PageURL = decodeURIComponent(window.location.search.substring(1)), URLVariables = PageURL
				.split('&'), search = "";
		for (i = 0; i < URLVariables.length; i++) {
			ParameterName = URLVariables[i].split('=');
			if (ParameterName[0] !== undefined
					&& ParameterName[1] !== undefined
					&& ParameterName[0] !== "locale") {
				search += ParameterName[0] + "=" + ParameterName[1] + "&";
			}
		}
		search += "locale=" + lang;
		window.location.search = search;
	};
})(jQuery);