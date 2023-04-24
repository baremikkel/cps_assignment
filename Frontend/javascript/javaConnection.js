function getData() {
    $.ajax({
        url: "localhost:8080/data",
        success: function(data) {
            $("#message").text(data.message);
        }
    });
}

$(document).ready(function() {
    getData();
});
