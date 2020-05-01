<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<title>Asiakkaat</title>
<style>

body {
	margin: 0;
}
table {
	border: 1px solid grey;
}
th {
	padding: 10px;
	min-width: 100px;
	margin: 0;
}
.oikealle{
	text-align: right;
}
.ylapalkki {
	background-color: darkgreen;
	padding: 5px;
	color: white;
}

.tiedot > tr {
	background-color: white;
	
}
.tiedot > tr:nth-child(even) {
	background-color: lightgrey;
}
.tiedot > tr > td {
	padding: 10px;
	border: 1px solid grey;
	margin: 0;
	}

</style>
</head>
<body>
<table id="listaus">
	<thead>	
		<tr>
			<th colspan="5" class="oikealle"><span id="uusiAsiakas">Lisää uusi asiakas</span></th>
		</tr>
		<tr class="ylapalkki">
			<th class="oikealle">Hakusana:</th>
			<th colspan="2"><input type="text" id="hakusana"></th>
			<th><input type="button" value="hae" id="hakunappi"></th>
		</tr>			
		<tr class="ylapalkki">
			<th>Etunimi</th>
			<th>Sukunimi</th>
			<th>Puhelin</th>
			<th>Sposti</th>							
		</tr>
	</thead>
	<tbody class="tiedot">
	</tbody>
</table>
<script>
$(document).ready(function(){
	
	$("#uusiAsiakas").click(function(){
		document.location="lisaaasiakas.jsp";
	});
	
	
	haeAsiakkaat();
	$("#hakunappi").click(function(){		
		haeAsiakkaat();
	});
	$(document.body).on("keydown", function(event){
		  if(event.which==13){ //Enteriä painettu, ajetaan haku
			  haeAsiakkaat();
		  }
	});
	$("#hakusana").focus();//viedään kursori hakusana-kenttään sivun latauksen yhteydessä
});	

function haeAsiakkaat(){
	$("#listaus tbody").empty();
	$.ajax({url:"asiakkaat/"+$("#hakusana").val(), type:"GET", dataType:"json", success:function(result){//Funktio palauttaa tiedot json-objektina		
		$.each(result.asiakkaat, function(i, field){  
        	var htmlStr;
        	htmlStr+="<tr  id='rivi_"+field.sposti+"'>";
        	htmlStr+="<td>"+field.etunimi+"</td>";
        	htmlStr+="<td>"+field.sukunimi+"</td>";
        	htmlStr+="<td>"+field.puhelin+"</td>";
        	htmlStr+="<td>"+field.sposti+"</td>"; 
        	htmlStr+="<td><a href='muutaasiakas.jsp?sposti="+field.sposti+"'>Muuta</a>&nbsp;";
        	htmlStr+="<td><span class='poista' onclick=poista('"+field.sposti+"')>Poista</span></td>";
        	htmlStr+="</tr>";
        	$("#listaus tbody").append(htmlStr);
        });	
    }});
}


function poista(sposti){
	if(confirm("Poista asiakas " + sposti +"?")){
		$.ajax({url:"asiakkaat/"+sposti, type:"DELETE", dataType:"json", success:function(result) { 
	        if(result.response==0){
	        	$("#ilmo").html("Asiakkaan poisto epäonnistui");
	        }else if(result.response==1){
	        	$("#rivi_"+sposti).css("background-color", "red");
	        	alert("Asiakkaan " + sposti +" poisto onnistui.");
	        	haeAsiakkaat();        	
			}
	    }});
	}
}
</script>
</body>
</html>