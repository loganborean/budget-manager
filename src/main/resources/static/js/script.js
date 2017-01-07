$(document).ready(function(){
    $('.filterable .btn-filter').click(function(){
        var $panel = $(this).parents('.filterable'),
        $filters = $panel.find('.filters input'),
        $tbody = $panel.find('.table tbody');
        if ($filters.prop('disabled') == true) {
            $filters.prop('disabled', false);
            $filters.first().focus();
        } else {
            $filters.val('').prop('disabled', true);
            $tbody.find('.no-result').remove();
            $tbody.find('tr').show();
        }
    });

    $('.filterable .filters input').keyup(function(e){
        /* Ignore tab key */
        var code = e.keyCode || e.which;
        if (code == '9') return;
        /* Useful DOM data and selectors */
        var $input = $(this),
        inputContent = $input.val().toLowerCase(),
        $panel = $input.parents('.filterable'),
        column = $panel.find('.filters th').index($input.parents('th')),
        $table = $panel.find('.table'),
        $rows = $table.find('tbody tr');
        /* Dirtiest filter function ever ;) */
        var $filteredRows = $rows.filter(function(){
            var value = $(this).find('td').eq(column).text().toLowerCase();
            return value.indexOf(inputContent) === -1;
        });
        /* Clean previous no-result if exist */
        $table.find('tbody .no-result').remove();
        /* Show all rows, hide filtered ones (never do that outside of a demo ! xD) */
        $rows.show();
        $filteredRows.hide();
        /* Prepend no-result row if all rows are filtered */
        if ($filteredRows.length === $rows.length) {
            $table.find('tbody').prepend($('<tr class="no-result text-center"><td colspan="'+ $table.find('.filters th').length +'">No result found</td></tr>'));
        }
    });
});


jQuery(document).ready(
	function($) {

		$("#update-chart").click(
				function(event) {

					var categoryId = $('#category').val();
					var dateFrom = $('#dateFrom').val();

					var url = '/trends/data?' + 'category=' + categoryId
							+ '&date=' + dateFrom;

					$.ajax({
						url : url,
						error : function() {
							alert('error')
						},
						dataType : 'json',
						success : function(data) {
							doThing(data);
						},
						type : 'GET'
					});

				});

	});

function doThing(data) {
	
	var monthTotals = data.monthTotals;
	var arr =[];
	for (var key in monthTotals) {
		if (monthTotals.hasOwnProperty(key)) {
			arr.unshift([key, monthTotals[key]]);

		    console.log(key + " -> " + monthTotals[key]);
		}	
	}
	arr.unshift(['Month', 'Total']);
	
	for (var i = 0; i < arr.length; i++) {
		for (var j = 0; j < arr[0].length; j++) {
			console.log(arr[i][j]);

		}
	}
}


$(document).ready(function(){

					var categoryId = $('#category').val();
					var dateFrom = $('#dateFrom').val();

					var url = '/trends/data?' + 'category=' + categoryId
							+ '&date=' + dateFrom;

					$.ajax({
						url : url,
						error : function() {
							alert('error')
						},
						dataType : 'json',
						success : function(data) {
							doThing(data);
						},
						type : 'GET'
					});

	
google.charts.load('current', {
	'packages' : [ 'bar' ]
});
google.charts.setOnLoadCallback(function() { drawStuff(arr) });

function drawStuff(msg) {
	var data = new google.visualization.arrayToDataTable([
			[ 'Move', 'Percentage' ], [ "King's pawn (e4)", 44 ],
			[ "Queen's pawn (d4)", 31 ], [ "Knight to King 3 (Nf3)", 12 ],
			[ "Queen's bishop pawn (c4)", 10 ], [ 'Other', 3 ] ]);
	alert(arr);

	var options = {
		title : 'Total Expenses Over Months',
		width : '100%',
		legend : {
			position : 'none'
		},
		chart : {
			subtitle : 'Amount by $'
		},
		axes : {
			x : {
				0 : {
					side : 'bottom',
					label : 'White to move'
				}
			// Top x-axis.
			}
		},
		bar : {
			groupWidth : "90%"
		}
	};

	var chart = new google.charts.Bar(document.getElementById('top_x_div'));
	// Convert the Classic options to Material options.
	chart.draw(data, google.charts.Bar.convertOptions(options));
};
	
	
	
});