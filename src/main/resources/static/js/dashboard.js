$(document).ready(function () {



});


function setProductId(card) {
    var productId = card.el.find('select').val();
    $.ajax({
        url: '/api/v1/version/list.json',
        type: 'GET',
        data: {productId: productId},
        dataType: 'json',
        success: function (response) {
            var options = "";
            $.each(response, function (i, version) {
                options += '<option value="' + version.id + '" version="' + version.version + '">' + version.version + '</option>';
            });
            card.wizard.cards['choose_version'].el.find('select').html(options);
        }
    });
}


function check_version(el) {
    var retValue = {};
    var productId = $('#version_choose_productId').val();
    var version = el.val();
    if (version == "") {
        retValue.status = false;
        retValue.msg = '请输入版本号';
        return retValue;
    }
    $.ajax({
        url: '/api/v1/version/check.json',
        type: 'POST',
        async: false,
        dataType: 'json',
        data: { productId: productId, version: version},
        success: function (response) {
            if (!response.success) {
                retValue.status = false;
                retValue.msg = version + '版本已存在';
            } else {
                retValue.status = true;
            }
        }
    });
    return retValue;
}


var uploadButton = $('<button id="beyond"/>')
    .addClass('btn btn-primary')
    .prop('disabled', true)
    .text('Processing...')
    .on('click', function () {
        var $this = $(this),
            data = $this.data();
        $this
            .off('click')
            .text('取消')
            .on('click', function () {
                $this.remove();
                data.abort();
            });
        data.form = null;
        data.submit().always(function () {
            $this.remove();
        });
    });

