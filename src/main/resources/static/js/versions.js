var versions;
$(document).ready(function () {
    versions = $("#version-list").DataTable({
        responsive: true,
        order: [
            [1, 'desc']
        ],
        "ajax": {
            "url": '/api/v1/version/list.json',
            "dataSrc": function (response) {
                return response;
            }
        },
        "columns": [
            {"data": "id"},
            {
                "data": "version",
                "render": function (data, type, full, meta) {
                    return '<span class="badge">' + data + '</span>';
                }
            },
            {"data": "product"},
            {
                "data": "download",
                "render": function (data, type, full, meta) {
                    if (data == null) {
                        return '';
                    }
                    return '<a href="' + data + '">' + data.split('/').pop() + '</a>';
                }
            },
            {
                "data": "pack",
                "render": function (data, type, full, meta) {
                    if (data == null) {
                        return '';
                    }
                    return '<a href="' + data + '">' + data.split('/').pop() + '</a>';
                }
            },
            {"data": "createAt"},
            {"data": "updateAt"},
            {"data": "state"},
            {"data": "count" }
        ]
    });

//    $('#version-list tbody').on('click', 'tr', function () {
//        if ($(this).hasClass('selected')) {
//            $(this).removeClass('selected');
//        }
//        else {
//            versions.$('tr.selected').removeClass('selected');
//            $(this).addClass('selected');
//        }
//    });

    $('#version-list tbody').on('dblclick', 'tr', function () {
        edit_product(versions.row(this).data(), versions);
    });

    $('#delete-version').on('click', function () {
        bootbox.confirm('确定删除?', function (result) {
            if (result) {
                var row = versions.row('.selected');
                $.ajax({
                    url: '/api/v1/version/ex/' + row.data().id,
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

    $('.version-state').on('click', function () {
        var row = versions.row('.selected');
        if (row != undefined) {
            var state = $(this).attr('state');
            $.ajax({
                url: '/api/v1/version/ex/state/' + row.data().id,
                type: 'PUT',
                dataType: 'json',
                async: false,
                data: {state: state},
                statusCode: {
                    204: function (response) {
                        Note.show('更新成功!');
                        versions.ajax.reload();
                    }
                }

            });
        }
    });

    $('#refresh-version').on('click', function () {
        versions.ajax.reload();
        Note.show('刷新成功!');
    });

    var wizard_version = $('#version-wizard').wizard({
        buttons: {
            nextText: '下一步',
            backText: '上一步',
            submitText: '提交',
            submittingText: '提交中...'
        }
    });

    wizard_version.on('submit', function (wizard) {
        $.ajax({
            url: '/api/v1/version/ex',
            dataType: 'json',
            type: 'post',
            async: false,
            data: wizard.serialize(),
            statusCode: {
                201: function (resp) {
                    Note.show('版本创建成功!');
                    wizard.trigger('success');
                    wizard.hideButtons();
                    wizard._submitting = false;
                    wizard.showSubmitCard('success');
                    //wizard.updateProgressBar(0);
                    versions.ajax.reload();
                    //wizard_version.reset();
                }
            },
            error: function () {
                wizard.submitError();
                wizard.hideButtons();
                Note.show('版本创建失败!');
                wizard_version.reset();
            }
        });
    });

    wizard_version.el.find(".wizard-success .im-done").click(function () {
        wizard_version.hide();
        setTimeout(function () {
            wizard_version.reset();
            versions.ajax.reload();
        }, 250);
    });

    wizard_version.el.find(".wizard-success .create-another").click(function () {
        wizard_version.reset();
        versions.ajax.reload();
    });

    wizard_version.on('reset', function (wizard) {
        //the fileupload plugin draw me crazy...
        wizard.updateProgressBar(0);
        location.reload();
    });


    wizard_version.cards['choose_product'].on('selected', function (card) {
        $.ajax({
            url: '/api/v1/product.json',
            type: 'GET',
            dataType: 'json',
            success: function (response) {
                var options = "";
                $.each(response.content, function (i, product) {
                    options += '<option value="' + product.id + '" code="' + product.code + '">' + product.name + '</option>';
                });
                var select = card.el.find('select').html(options);
            }
        });
    });

    wizard_version.cards['upload_program'].on('selected', function (card) {
        var product = wizard_version.el.find('select').find('option:selected').attr('code');
        var version = wizard_version.el.find('input[name=version]').val();
        $('#program_upload').fileupload({
            url: '/upload/file/' + product + '/' + version + '/latest.json',
            dataType: 'json',
            autoUpload: false,
            disableImageResize: /Android(?!.*Chrome)|Opera/
                .test(window.navigator.userAgent),
            previewMaxWidth: 100,
            previewMaxHeight: 100,
            previewCrop: true
        }).on('fileuploadadd',function (e, data) {
            data.context = $('<div class="file-preview"/>').appendTo('#files');
            $.each(data.files, function (index, file) {
                var node = $('<p/>')
                    .append($('<span/>').text(file.name));
                if (!index) {
                    node
                        .append('<br>')
                        .append(uploadButton.clone(true).data(data));
                }
                node.appendTo(data.context);
            });
        }).on('fileuploadprocessalways',function (e, data) {
            var index = data.index,
                file = data.files[index],
                node = $(data.context.children()[index]);
            if (file.preview) {
                node
                    .prepend('<br>')
                    .prepend(file.preview);
            }
            if (file.error) {
                node
                    .append('<br>')
                    .append($('<span class="text-danger"/>').text(file.error));
            }
            if (index + 1 === data.files.length) {
                data.context.find('button')
                    .text('上传')
                    .prop('disabled', !!data.files.error);
            }
        }).on('fileuploadprogressall',function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress .progress-bar').css(
                'width',
                progress + '%'
            );
        }).on('fileuploaddone',function (e, data) {
            $.each(data.result.files, function (index, file) {
                if (file.url) {
                    var link = $('<a>')
                        .attr('target', '_blank')
                        .prop('href', file.url);
                    $(data.context.children()[index])
                        .wrap(link);
                    data.context.append('<input type="hidden" name="download" value="' + file.relative + '">');
                } else if (file.error) {
                    var error = $('<span class="text-danger"/>').text(file.error);
                    $(data.context.children()[index])
                        .append('<br>')
                        .append(error);
                }
            });
        }).on('fileuploadfail',function (e, data) {
            $.each(data.files, function (index) {
                var error = $('<span class="text-danger"/>').text('上传失败.');
                $(data.context.children()[index])
                    .append('<br>')
                    .append(error);
            });
        }).prop('disabled', !$.support.fileInput)
            .parent().addClass($.support.fileInput ? undefined : 'disabled');
    });

    wizard_version.cards['upload_pack'].on('selected', function (card) {
        var product = wizard_version.el.find('select').find('option:selected').attr('code');
        var version = wizard_version.el.find('input[name=version]').val();
        updatePkg = $('#pack_upload').fileupload({
            url: '/upload/file/' + product + '/' + version + '/update.json',
            maxNumberOfFiles: 1,
            dataType: 'json',
            autoUpload: false,
            disableImageResize: /Android(?!.*Chrome)|Opera/
                .test(window.navigator.userAgent),
            previewMaxWidth: 100,
            previewMaxHeight: 100,
            previewCrop: true
        }).on('fileuploadadd',function (e, data) {
            data.context = $('<div class="file-preview"/>').appendTo('#files0');
            $.each(data.files, function (index, file) {
                var node = $('<p/>')
                    .append($('<span/>').text(file.name));
                if (!index) {
                    node
                        .append('<br>')
                        .append(uploadButton.clone(true).data(data));
                }
                node.appendTo(data.context);
            });
        }).on('fileuploadprocessalways',function (e, data) {
            var index = data.index,
                file = data.files[index],
                node = $(data.context.children()[index]);
            if (file.preview) {
                node
                    .prepend('<br>')
                    .prepend(file.preview);
            }
            if (file.error) {
                node
                    .append('<br>')
                    .append($('<span class="text-danger"/>').text(file.error));
            }
            if (index + 1 === data.files.length) {
                data.context.find('button')
                    .text('上传')
                    .prop('disabled', !!data.files.error);
            }
        }).on('fileuploadprogressall',function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress0 .progress-bar').css(
                'width',
                progress + '%'
            );
        }).on('fileuploaddone',function (e, data) {
            $.each(data.result.files, function (index, file) {
                if (file.url) {
                    var link = $('<a>')
                        .attr('target', '_blank')
                        .prop('href', file.url);
                    $(data.context.children()[index])
                        .wrap(link);
                    data.context.append('<input type="hidden" name="pack" value="' + file.relative + '">');
                } else if (file.error) {
                    var error = $('<span class="text-danger"/>').text(file.error);
                    $(data.context.children()[index])
                        .append('<br>')
                        .append(error);
                }
            });
        }).on('fileuploadfail',function (e, data) {
            $.each(data.files, function (index) {
                var error = $('<span class="text-danger"/>').text('上传失败.');
                $(data.context.children()[index])
                    .append('<br>')
                    .append(error);
            });
        }).prop('disabled', !$.support.fileInput)
            .parent().addClass($.support.fileInput ? undefined : 'disabled');
    });


    $('.add-version').on('click', function () {
        wizard_version.show();
    });

    wizard_version.on('closed', function () {
        wizard_version.reset();
    });


});


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

function cleanUpload() {
    $('.file-preview').remove();
    $('#progress .progress-bar').css(
        'width',
        '0%'
    );
}

var uploadButton = $('<button/>')
    .addClass('btn btn-primary')
    .prop('disabled', true)
    .prop('type', 'button')
    .text('处理中...')
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
        data.submit().always(function () {
            $this.remove();
        });
    });

function edit_product(version, table) {
    var form = [
        '<form id="form_edit_version" action="/api/v1/version/' + version.id + '" method="PUT">',
        '<div class="form-group input-group">',
        '<span class="input-group-addon">版本号</span>',
        '<input class="form-control" id="version_curr" placeholder="请输入版本号" name="version" value="' + ((version.version == null) ? "" : version.version) + '">',
        '</div>',
        '<div class="form-group input-group" id="download">',
        '<span class="input-group-addon">安装包</span>',
        '<input class="form-control" id="download_url" placeholder="安装包" name="download" value="' + ((version.download == null) ? "" : version.download) + '"/>',
        '<span class="input-group-btn"><button class="btn btn-danger remove-file" type="button" package="download"><i class="glyphicon glyphicon-remove" ></i></button></span>',
        '</div>',
        '<div class="form-group input-group" id="pack">',
        '<span class="input-group-addon">更新包</span>',
        '<input class="form-control" id="pack_url" placeholder="上传更新包" name="pack" value="' + ((version.pack == null) ? "" : version.pack) + '"/>',
        '<span class="input-group-btn"><button class="btn btn-danger remove-file" type="button" package="pack"><i class="glyphicon glyphicon-remove" ></i></button></span>',
        '</div>',
        '<div class="form-group input-group row-fluid">',
        '<span class="input-group-addon">版本锁</span>',
        '<select class="form-control state-picker" data-style="btn-warning" data-width="auto" name="state" value="' + version.state + '"><option value="RELEASE">发布</option><option value="DEBUG">测试</option><option value="INIT">初始</option>',
        '</div>',

        '</form>'
    ];
    bootbox.dialog({
        title: '<span class="label label-primary pull-right">' + version.product + '<span class="badge">' + version.version + '</span></span><span class="label label-danger">' + version.id + '</span>',
        message: form.join(''),
        backdrop: true,
        closeButton: true,
        buttons: {
            warning: {
                label: '<i class="glyphicon glyphicon-remove-circle"></i>删除版本',
                className: 'btn-danger',
                callback: function () {
                    bootbox.confirm('确定删除?', function (result) {
                        if (result) {
                            $.ajax({
                                url: '/api/v1/version/ex/' + version.id,
                                type: 'DELETE',
                                dataType: 'json',
                                async: false,
                                statusCode: {
                                    204: function (response) {
                                        Note.show('删除成功!');
                                        versions.ajax.reload();
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
                        url: '/api/v1/version/ex/' + version.id,
                        type: 'PUT',
                        dataType: 'json',
                        data: $('#form_edit_version').serialize(),
                        statusCode: {
                            204: function (response) {
                                table.ajax.reload();
                                Note.show('编辑成功');
                            }
                        }
                    });
                }
            }
        }
    });

    $('.state-picker').selectpicker();

    $('.remove-file').on('click', function () {
        $(this).addClass('disabled').removeClass('btn-danger').addClass('btn-warning');
        var type = $(this).attr('package');
        var url = $('#' + type + "_url").val();
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
            $('#' + type + "_url").val('');
        }
        var upload = [
            '<span class="btn btn-success fileinput-button">',
            '<i class="glyphicon glyphicon-plus"></i>',
            '<span>选择文件</span>',
            '<input id="' + type + '_upload_edit" type="file" name="files[]" multiple>',
            '</span><br><br>',
            '<div id="progress_' + type + '" class="progress">',
            '<div class="progress-bar progress-bar-success progress-bar-striped active" role="progressbar"></div>',
            '</div>',
            '<div id="files_' + type + '" class="files"></div><br>'
        ];
        $('#' + type).after(upload.join(''));
        var currVersion = $('#version_curr').val();
        var tail = (type == 'pack') ? '/update.json' : '/latest.json';
        var uploadUrl = '/upload/file/' + version.product + '/' + currVersion + tail;
        $('#' + type + '_upload_edit').fileupload({
            url: uploadUrl,
            dataType: 'json',
            add: function (e, data) {
                $('#files_' + type).html('');
                data.context = $('<button/>').prop('type', 'button').addClass('btn btn-primary').text('上传')
                    .appendTo($('#files_' + type))
                    .click(function () {
                        data.context = $(this).text('处理中...').removeClass('btn-primary').addClass('btn-warning');
                        data.submit();
                    });
            },
            progressall: function (e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                $('#progress_' + type + ' .progress-bar').css(
                    'width',
                    progress + '%'
                ).text(progress + '%');
            },
            done: function (e, data) {
                var file = data.response().result.files[0];
                $('#' + type + '_url').val(file.name);
                data.context.removeClass('btn-warning').addClass('btn-primary').html('<i class="glyphicon glyphicon-ok"></i>上传成功<a class="pull-right" href="' + file.url + '">');
            },
            fail: function (e, data) {
                data.context.removeClass('btn-warning').addClass('btn-danger').html('<i class="glyphicon glyphicon-exclamation-sign"></i>上传失败');
            }
        });
    });
}

