$(document).ready(function() {
	google.charts.load('current', {'packages':['corechart']});
	google.charts.setOnLoadCallback(drawPieChart);
	$(".clickable").click(function(event) {

		var children;

		if (event.target.nodeName === "P") {
			children = event.target.parentNode.children;
		} else if (event.target.nodeName === "DIV") {
			children = event.target.children;
		} else {
			alert("error");
		}

		var monthYear = children[0].innerHTML;

		var url = '/spending/history/data';
		$.ajax({
			type : 'POST',
			url : url,
			data : { "monthYear" : monthYear },
			error : function() {
				alert('There was an error')
			},
			dataType : 'json',
			success : function(data) {
				drawPieChart(data);
			}
		});
		
	});	
});


function drawPieChart(respData) {


	var categoryAmountPair = respData.categoryAmountPair;

	var dataArray = [];
	dataArray.push(['Category', 'Amount']);

	for (var categoryName in categoryAmountPair) {
		if (categoryAmountPair.hasOwnProperty(categoryName)) {
			dataArray.push([categoryName, categoryAmountPair[categoryName]]);
		}	
	}

	var dataTable = google.visualization.arrayToDataTable(dataArray);

	var options = {
	  title: 'Spending by Category',
	  width: '100%',
	  height: '100%'
	};

	var element = document.getElementById('spendingHistoryPieChart');
	var chart = new google.visualization.PieChart(element);

	chart.draw(dataTable, options);
}