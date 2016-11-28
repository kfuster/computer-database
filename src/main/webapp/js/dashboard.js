//On load
$(function() {
    // Default: hide edit mode
    $(".editMode").hide();
    
    // Click on "selectall" box
    $("#selectall").click(function () {
        $('.cb').prop('checked', this.checked);
    });

    // Click on a checkbox
    $(".cb").click(function() {
        if ($(".cb").length == $(".cb:checked").length) {
            $("#selectall").prop("checked", true);
        } else {
            $("#selectall").prop("checked", false);
        }
        if ($(".cb:checked").length != 0) {
            $("#deleteSelected").enable();
        } else {
            $("#deleteSelected").disable();
        }
    });

});


// Function setCheckboxValues
(function ( $ ) {

    $.fn.setCheckboxValues = function(formFieldName, checkboxFieldName) {

        var str = $('.' + checkboxFieldName + ':checked').map(function() {
            return this.value;
        }).get().join();
        
        $(this).attr('value',str);
        
        return this;
    };

}( jQuery ));

// Function toggleEditMode
(function ( $ ) {

    $.fn.toggleEditMode = function() {
        if($(".editMode").is(":visible")) {
            $(".editMode").hide();
            $("#editComputer").text("Edit");
        }
        else {
            $(".editMode").show();
            $("#editComputer").text("View");
        }
        return this;
    };

}( jQuery ));


// Function delete selected: Asks for confirmation to delete selected computers, then submits it to the deleteForm
(function ( $ ) {
    $.fn.deleteSelected = function() {
        if (confirm("Are you sure you want to delete the selected computers?")) { 
            $('#deleteForm input[name=selection]').setCheckboxValues('selection','cb');
            $('#deleteForm').submit();
        }
    };
}( jQuery ));

(function ( $ ) {
	$.fn.columnSort = function(value) {
		var PageURL = decodeURIComponent(window.location.search.substring(1)),
		URLVariables = PageURL.split('&'),
        parameterName,
        parameterValue,
        search="",
        found = false,
        same = false,
        i;
		for (i = 0; i < URLVariables.length; i++) {
	        ParameterName = URLVariables[i].split('=');
	        if(ParameterName[0] && ParameterName[0] !== "order") {
		        search += ParameterName[0] + "=";
		        if (ParameterName[0] === "column") {
		        	found = true;
		        	if(ParameterName[1] === value) {
		        		same = true;
		        		search += value;	
		        	}
		        	else {
			            search += value + "&order=ASC&";
		        	}
		        }
		        else {
		        	search += ParameterName[1] + "&";
		        }
	        }
	        else if (ParameterName[0] === "order" && same) {
	        	if(ParameterName[1] === "ASC") {
	        		search += "&order=DESC";
	        	}
	        	else {
	        		search += "&order=ASC";
	        	}
	        }
	    }
		if (!found) {
			var column = "";
			if(window.location.search.length > 0) {
				column = "&"
			}
			column += "column=" + value + "&order=ASC";
			search += column
		}
		window.location.search = search;
	};
}( jQuery ));

//Event handling
//Onkeydown
$(document).keydown(function(e) {

    switch (e.keyCode) {
        //DEL key
        case 46:
            if($(".editMode").is(":visible") && $(".cb:checked").length != 0) {
                $.fn.deleteSelected();
            }   
            break;
        //E key (CTRL+E will switch to edit mode)
        case 69:
            if(e.ctrlKey) {
                $.fn.toggleEditMode();
            }
            break;
    }
});
