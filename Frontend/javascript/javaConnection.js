function getData() {
    $.ajax({
        url: "http://localhost:8080/data",
        success: function(data) {
            $("#message").html(data.message);
        }
    });
}

$(document).ready(function() {
    getData();
});
