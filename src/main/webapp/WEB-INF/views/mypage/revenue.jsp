<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<input type="hidden" id="loginId" value="${loginUser.user_id }" />
<div id="chart_div" style="width: 800px; height: 500px;"></div>
<script>
	function chart(){
		var loginId = $("#loginId").val();
		$.ajax({
	        url: '${pageContext.request.contextPath}/chart',
	        type: 'get',
	        data: {loginId : loginId},
	        async: false,
	        success: function(resp) {
	        	google.charts.load('current', {'packages':['corechart', 'bar']});
	        	google.charts.setOnLoadCallback(drawStuff);
	        	
	        	function drawStuff() {
	        		var chartDiv = document.getElementById('chart_div');
	        		
	        		var data = new google.visualization.DataTable();
	    			data.addColumn('date', '월');
	    			data.addColumn('number', "수익");
	    			data.addColumn('number', "거래량");
	    			
	    			function parse(str){
	    				var str = ''+str;
	    				var y = str.substr(0,4);
	    				var m = str.substr(5,2);
	    				return new Date(y,m-1);
	    			};
	    			
	    			$.each(resp, function(i, rev){
	    				data.addRow([parse(rev.month), rev.sum, rev.count]);
	    			});
	    			
	    			var materialOptions = {
	    				width : 900,
	    				colors: ['#17a2b8', '#117888'],
	    				chart:{
	    					title : "수익정보"
	    				}, 
	    				series: {
    						0: {axis : "Revenue"},
    						1: {axis : "SalesRate"}
    					},
    					axes:{
    						y:{
    							Revenue:{label:"수익"},
    							SalesRate:{label:"거래량"}
    						}
    					}
	    			};
	    			
	    			var materialChart = new google.charts.Bar(chartDiv);
	    			materialChart.draw(data, google.charts.Bar.convertOptions(materialOptions));

	        	};
	        }
	    });
	};
	chart();


</script>