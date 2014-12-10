var logs;
$(function () {
    logs = $("#changelog-list").DataTable({
        responsive: true,
        "ajax": {
            "url": '/api/v1/changelog.json',
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
            {"data": "title"},
            {"data": "description"},
            {
                "data": "image",
                "render": function (data, type, full, meta) {
                    return '<div class="img-thumb" href="' + data + '"><img src="' + data + '" class="img-thumbnail"></div>'; //
                }
            },
            {"data": "width"},
            {"data": "height"},
            {"data": "createAt"},
            {"data": "updateAt"}
        ],
        initComplete: function () {
            var api = this.api();
            api.$('.img-thumb').click(function () {
                bootbox.dialog({
                    message: '<img src="' + $(this).attr('href') + '" width="550" >'
                });
            })
        }
    });

    $('#changelog-list tbody').on('dblclick', 'tr', function () {
        var row = logs.row(this);
        if (row.data() == undefined) return;


        edit_log(row.data(), logs);
    });

    $('#delete-log').on('click', function () {
        var row = logs.row('.selected');
        bootbox.confirm('确定删除该日志?', function (result) {
            if (result) {
                $.ajax({
                    url: '/api/v1/changelog/' + row.data().id,
                    type: 'DELETE',
                    dataType: 'json',
                    async: false,
                    statusCode: {
                        204: function (response) {
                            Note.show('删除成功!');
                            row.remove().draw(false);
                        }
                    }
                });
            }
        });
    });

    $('.refresh-log').on('click', function () {
        logs.ajax.reload();
        Note.show('刷新成功', 100);
    });

    var wizard_log = $("#log-wizard").wizard({
        contentHeight: 400,
        contentWidth: 750,
        buttons: {
            nextText: '下一步',
            backText: '上一步',
            submitText: '提交',
            submittingText: '提交中...'
        }
    });


    wizard_log.on('submit', function (wizard) {
        $.ajax({
            url: '/api/v1/changelog',
            dataType: 'json',
            type: 'post',
            data: wizard.serialize(),
            statusCode: {
                201: function (resp) {
                    Note.show('日志创建成功!');
                    wizard.trigger('success');
                    wizard.hideButtons();
                    wizard._submitting = false;
                    wizard.showSubmitCard('success');
                    wizard.updateProgressBar(0);
                    logs.ajax.reload();
                }
            },
            error: function () {
                wizard.submitError();
                wizard.hideButtons();
                Note.show('日志创建失败!');
            }
        });
    });

    wizard_log.on('closed', function () {
        wizard_log.reset();
    });

    wizard_log.on('reset', function (wizard) {
        //clear input

        $.each(wizard.cards, function (name, card) {
            card.el.find('input').val('');
        });

        $('#change_img_upload').fileupload('destroy');

        location.reload();

    });

    $('.add-log').on('click', function () {
        wizard_log.show();
    });


    wizard_log.cards['choose_product'].on('selected', function (card) {
        $.ajax({
            url: '/api/v1/product.json',
            type: 'GET',
            dataType: 'json',
            success: function (response) {
                var options = "";
                $.each(response.content, function (i, product) {
                    options += '<option value="' + product.id + '" code="' + product.code + '">' + product.name + '</option>';
                });
                card.el.find('select').html(options);
            }
        });
    });

    wizard_log.el.find(".wizard-success .im-done").click(function () {
        wizard_log.hide();
        setTimeout(function () {
            wizard_log.reset();
        }, 250);
    });

    wizard_log.el.find(".wizard-success .create-another").click(function () {
        wizard_log.reset();
    });


    var card_upload_img = wizard_log.cards['upload_image'];
    card_upload_img.on('selected', function (card) {
        var product = wizard_log.el.find('select[name=productId]').find('option:selected').attr('code');
        var version = wizard_log.el.find('select[name=versionId]').find('option:selected').attr('version');
        $('#change_img_upload').fileupload({
            url: '/upload/image/' + product + '/' + version + '.json',
            maxNumberOfFiles: 1,
            acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i
        });
    });


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

function edit_log(log, logs) {
    var form = [
        '<form id="form_edit_log" action="/api/v1/changelog/' + log.id + '" method="PUT">',
        '<div class="form-group input-group">',
        '<span class="input-group-addon">标题</span>',
        '<input class="form-control" id="version" placeholder="请输入标题" name="title" value="' + ((log.title == null) ? "" : log.title) + '">',
        '</div>',
        '<div class="form-group input-group">',
        '<span class="input-group-addon">描述</span>',
        '<input class="form-control" id="description" placeholder="请输入描述" name="description" value="' + ((log.description == null) ? "" : log.description) + '"/>',
        '</div>',
        '<div class="form-group input-group">',
        '<span class="input-group-addon">大小</span>',
        '<input class="form-control" id="width" name="width" value="' + ((log.width == null) ? "" : log.width) + '"/>',
        '<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>',
        '<input class="form-control" id="height" name="height" value="' + ((log.height == null) ? "" : log.height) + '"/>',
        '</div>',
        '<div class="form-group input-group" id="image">',
        '<span class="input-group-addon">图片</span>',
        '<input class="form-control" id="image_url" placeholder="上传图片" name="image" value="' + ((log.image == null) ? "" : log.image) + '"/>',
        '<span class="input-group-btn"><button class="btn btn-danger remove-file" type="button"><i class="glyphicon glyphicon-remove" ></i></button></span>',
        '</div>',
        '</form>'
    ];
    bootbox.dialog({
        title: '<span class="label label-primary pull-right">' + log.product + '<span class="badge">' + log.version + '</span></span><span class="label label-danger">' + log.id + '</span>',
        message: form.join(''),
        backdrop: true,
        closeButton: true,
        buttons: {
            warning: {
                label: '<i class="glyphicon glyphicon-remove-circle"></i>删除日志',
                className: 'btn-danger',
                callback: function () {
                    bootbox.confirm('确定删除?', function (result) {
                        if (result) {
                            $.ajax({
                                url: '/api/v1/changelog/' + log.id,
                                type: 'DELETE',
                                dataType: 'json',
                                async: false,
                                statusCode: {
                                    204: function (response) {
                                        Note.show('删除成功!');
                                        logs.ajax.reload();
                                    }
                                }
                            });
                        } else {
                            Note.show('已放弃删除');
                        }
                    });

                }
            },
            success: {
                label: '<span class="glyphicon glyphicon-ok-circle"></span>保存编辑',
                className: 'btn-success',
                callback: function () {
                    $.ajax({
                        url: '/api/v1/changelog/' + log.id,
                        type: 'PUT',
                        dataType: 'json',
                        data: $('#form_edit_log').serialize(),
                        statusCode: {
                            204: function (response) {
                                logs.ajax.reload();
                                Note.show('编辑成功');
                            }
                        }
                    });
                }
            }
        }
    });

    $('.remove-file').on('click', function () {
        $(this).addClass('disabled').removeClass('btn-danger').addClass('btn-warning');
        var url = $('#image_url').val();
        if (url != '') {
            /*$.ajax({
             url: url,
             type: 'delete',
             dataType: 'json',
             statusCode: {
             204: function (response) {
             Note.show('文件已删除');
             $('#' + type + "_url").val('');
             }
             }
             })*/
            $('#image_url').val('');
        }
        var upload = [
            '<span class="btn btn-success fileinput-button">',
            '<i class="glyphicon glyphicon-plus"></i>',
            '<span>选择文件</span>',
            '<input id="image_upload_edit" type="file" name="files[]" multiple>',
            '</span><br><br>',
            '<div id="progress_image" class="progress">',
            '<div class="progress-bar progress-bar-success progress-bar-striped active" role="progressbar"></div>',
            '</div>',
            '<div id="files" class="files"></div><br>'
        ];
        $('#image').after(upload.join(''));
        var uploadUrl = '/upload/image/' + log.product + '/' + log.version + '.json';
        $('#image_upload_edit').fileupload({
            url: uploadUrl,
            dataType: 'json',
            add: function (e, data) {
                $('#files').html('');
                data.context = $('<button/>').prop('type', 'button').addClass('btn btn-primary').text('上传')
                    .appendTo($('#files'))
                    .click(function () {
                        data.context = $(this).text('处理中...').removeClass('btn-primary').addClass('btn-warning');
                        data.submit();
                    });
            },
            progressall: function (e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                $('#progress_image .progress-bar').css(
                    'width',
                    progress + '%'
                ).text(progress + '%');
            },
            done: function (e, data) {
                var file = data.response().result.files[0];
                $('#image_url').val(file.name);
                $('.img-thumb').remove();
                $('#width').val(file.width);
                $('#height').val(file.height);
                data.context.removeClass('btn-warning').addClass('btn-primary').html('<i class="glyphicon glyphicon-ok"></i>上传成功<a class="pull-right" href="' + file.url + '">');
                $('#image').after('<div class="img-thumb" href="' + file.name + '"><img src="' + file.name + '" class="img-thumbnail"></div><br>');
            },
            fail: function (e, data) {
                data.context.removeClass('btn-warning').addClass('btn-danger').html('<i class="glyphicon glyphicon-exclamation-sign"></i>上传失败');
            }
        });
    });
}
