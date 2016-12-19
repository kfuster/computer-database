$("#introduced").datepicker({
	dateFormat : 'yy-mm-dd',
	changeMonth : true,
	changeYear : true,
	minDate : new Date(1970, 1 - 1, 1)
});
$("#discontinued").datepicker({
	dateFormat : 'yy-mm-dd',
	changeMonth : true,
	changeYear : true
});
$.validator.addMethod("validDate", function(value, element) {
	if (value != "") {
		return value.match(/^\d{4}-0[1-9]|1[0-2]-0[1-9]|1[0-9]|2[0-9]|3[0-1]$/);
	}
	return true;
}, "Entrez la date au format yyyy-mm-dd.");
/*$('#addForm').validate({
	rules : {
		introduced : {
			required : false,
			validDate : true
		},
		discontinued : {
			required : false,
			validDate : true
		},
		name : {
			required : true,
			minlength : 2
		}
	},
	messages : {
		name : {
			required : "Entrez un nom",
			minlength : "Le nom doit être d'au moins 2 caractères"
		},
		introduced : "Entrez la date au format yyyy-mm-dd.",
		discontinued : "Entrez la date au format yyyy-mm-dd."
	}
});*/