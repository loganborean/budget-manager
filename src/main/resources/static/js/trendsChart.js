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
							alert('There was an error')
						},
						dataType : 'json',
						success : function(data) {
							drawGraph(data);
							showSummary(data);
						},
						type : 'GET'
					});

				});

	});

function showSummary(data) {
		var monthTotals = data.monthTotals;
		var average = data.average;
		var grandTotal = data.grandTotal;
		
		var mostSpent = data.mostSpent;
		var leastSpent = data.leastSpent;
		
		var tableHtml = "";
		for (var key in monthTotals) {
			if (monthTotals.hasOwnProperty(key)) {
				tableHtml += '<tr><td>' + key + '</td><td>$' + parseFloat(monthTotals[key]).toFixed(2) + '</td></tr>';
			}	
		}
		tableHtml += '<tr><td style="font-weight: bold">Total</td><td style="font-weight: bold">$' + 
						parseFloat(grandTotal).toFixed(2) + '</td> </tr>';

		$('#monthly-spending-table').find('tbody').html(tableHtml);
		
		
		
		var summaryHtml  = '<div><p class="spendingSummaryHeading" >Most Spent</p>' + 
						   '<p class="spendingSummaryAmount">$' + mostSpent.amount + 
						   '</p><p>In ' + mostSpent.monthYear + '</p> <hr/> </div>';

		summaryHtml  += '<div><p class="spendingSummaryHeading" >Least Spent</p>' + 
					    '<p class="spendingSummaryAmount">$' + leastSpent.amount + 
					    '</p><p>In ' + leastSpent.monthYear + '</p> <hr/> </div>';

		summaryHtml  += '<div><p class="spendingSummaryHeading" >Average</p>' + 
					    '<p class="spendingSummaryAmount">$' + average +
					    '</p><p>Over Each Month</p> <hr/> </div>';
		
		$('#spending-summary').html(summaryHtml);
		
	
}

function drawGraph(data) {
	
	var monthTotals = data.monthTotals;
	var arr =[];
	for (var key in monthTotals) {
		if (monthTotals.hasOwnProperty(key)) {
			arr.unshift([key, monthTotals[key]]);
		}	
	}
	arr.unshift(['Month', 'Total']);
	
	
	google.charts.load('current', {
		'packages' : [ 'bar' ]
	});
	google.charts.setOnLoadCallback(function() { drawExpenseGraph(arr) });

}

function drawExpenseGraph(arr) {
	var data = new google.visualization.arrayToDataTable(arr);

	var options = {
		title : 'Expenses By Month',
		width : '100%',
		legend : {
			position : 'none'
		},
		chart : {
			subtitle : 'Amount in $'
		},
		axes : {
			x : {
				0 : {
					side : 'bottom',
					label : 'Month'
				}
			// Top x-axis.
			}
		},
		bar : {
			groupWidth : "90%"
		}
	};

	var chart = new google.charts.Bar(document.getElementById('top_x_div'));
	chart.draw(data, google.charts.Bar.convertOptions(options));
}


$(document).ready(
	function(){
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
				drawGraph(data);
				showSummary(data);
			},
			type : 'GET'
	});
});
	
	
	