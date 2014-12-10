$(document).ready(function () {
    var feedbacks = $("#feedback-list").DataTable({
        order: [
            [1, "desc"]
        ],
        responsive: true,

        "ajax": {
            "url": '/api/v1/feedback/list.json',
            "dataSrc": function (response) {
                return response;
            }
        },
        "columns": [
            {"data": "id"},
            {"data": "product"},
            {
                "data": "version",
                "render": function (data, type, full, meta) {
                    return '<span class="badge">' + data + '</span>';
                }
            },
            {"data": "remark"},
            {
                "data": "ip",
                "render": function (data, type, full, meta) {
                    return '<span class="badge">' + data + '</span>';
                }
            },
            {"data": "createAt"},
        ]
    });



    $('#refresh-feedback').on('click', function () {
        feedbacks.ajax.reload();
    });

});

