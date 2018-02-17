function send() {
    $.ajax({
        url: 'resources/appuser',
        type: 'PUT',
        data: JSON.stringify({
            fullName: 'arcadio',
            userName: 'arcadio',
            password: 'as12dAasdf'
        }),
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success: function (result) {
            // Do something with the result
        }
    });
}