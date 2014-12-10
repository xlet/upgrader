var products;
$(function () {
    products = $("#product-list").DataTable({
        responsive: true,
        "ajax": {
            "url": '/api/v1/product/list.json',
            "dataSrc": function (response) {
                return response;
            }
        },
        "columns": [
            {"data": "id"},
            {"data": "code"},
            {"data": "name"},
            {"data": "homepage"},
            {
                "data": "code",
                "render": function (data, type, full, meta) {
                    return '<a href="/' + data + '/timeline">timeline</a>'; //
                }
            },
            {"data": "createAt"},
            {"data": "updateAt"}
        ]
    });

    var wizard_product = $('#wizard-product').wizard({
        contentHeight: 400,
        contentWidth: 700,
        buttons: {
            nextText: '下一步',
            backText: '上一步',
            submitText: '提交',
            submittingText: '提交中...'
        }
    });

    wizard_product.el.find(".wizard-success .im-done").click(function () {
        wizard_product.hide();
        setTimeout(function () {
            wizard_product.reset();
            products.ajax.reload();
        }, 250);
    });

    wizard_product.el.find(".wizard-success .create-another").click(function () {
        wizard_product.reset();
        products.ajax.reload();
    });

    wizard_product.on('reset', function (wizard) {
        wizard.modal.find(':input').val('');
    });


    wizard_product.on('submit', function (wizard) {
        $.ajax({
            url: '/api/v1/product',
            dataType: 'json',
            type: 'post',
            data: wizard.serialize(),
            statusCode: {
                201: function (resp) {
                    Note.show('产品创建成功!');
                    wizard.trigger('success');
                    wizard.hideButtons();
                    wizard._submitting = false;
                    wizard.showSubmitCard('success');
                    wizard.updateProgressBar(0);
                    products.ajax.reload();
                }
            },
            error: function () {
                wizard.submitError();
                wizard.hideButtons();
                Note.show('产品创建失败!');
            }
        });
    });

    $('.refresh-product').on('click', function () {
        products.ajax.reload();
        Note.show('刷新成功',100);
    });

    $('.add-product').on('click', function () {
        wizard_product.show();
    });


    $('#delete-product').on('click', function () {
        bootbox.confirm("确定删除?", function (result) {
            if (result) {
                var row = products.row('.selected');
                $.ajax({
                    url: '/api/v1/product/' + row.data().id,
                    type: 'DELETE',
                    dataType: 'json',
                    async: false,
                    statusCode: {
                        204: function () {
                            row.remove().draw(false);
                            Note.show("删除成功！");
                        }
                    }
                });
            }
        });
    });


    $('#product-list tbody').on('dblclick', 'tr', function () {
        var row = products.row(this);
        if (row.data() == undefined) return;
        edit_product(row.data(), products);
    });


});

function check_product(code) {
    var ret = {};
    if (code.val() == "") {
        ret.status = false;
        ret.msg = '请输入产品代号';
        return ret;
    }
    $.ajax({
        url: '/api/v1/product/check.json',
        type: 'POST',
        async: false,
        dataType: 'json',
        data: { code: code.val()},
        success: function (response) {
            if (!response.success) {
                ret.status = false;
                ret.msg = code.val() + '已存在';
            } else {
                ret.status = true;
            }
        }
    });
    return ret;
}


function edit_product(product, table) {
    var form = [
        '<form id="form_edit_product" action="/api/v1/product/' + product.id + '" method="PUT">',
        '<div class="form-group input-group">',
        '<span class="input-group-addon">产品名称</span>',
        '<input class="form-control" id="name" placeholder="请输入产品名称" name="name" value="' + product.name + '">',
        '<span class="input-group-btn"><button class="btn btn-danger clean-input" type="button" f="name"><i class="glyphicon glyphicon-remove" ></i></button></span>',
        '</div>',
        '<div class="form-group input-group">',
        '<span class="input-group-addon">产品代号</span>',
        '<input class="form-control" id="code" placeholder="请设置产品代号" name="code" value="' + product.code + '">',
        '<span class="input-group-btn"><button class="btn btn-danger clean-input" type="button" f="code"><i class="glyphicon glyphicon-remove" ></i></button></span>',
        '</div>',
        '<div class="form-group input-group">',
        '<span class="input-group-addon">产品主页</span>',
        '<input class="form-control" id="homepage" placeholder="请设置产品主页" name="homepage" value="' + product.homepage + '">',
        '<span class="input-group-btn"><button class="btn btn-danger clean-input" type="button" f="homepage"><i class="glyphicon glyphicon-remove" ></i></button></span>',
        '</div>',
        '</div>',
        '</form>'
    ];
    bootbox.dialog({
        title: '编辑产品',
        message: form.join(''),
        backdrop: true,
        closeButton: true,
        buttons: {
            warning: {
                label: '删除产品',
                className: 'btn-danger',
                callback: function () {
                    bootbox.confirm('确定删除?', function (result) {
                        if (result) {
                            $.ajax({
                                url: '/api/v1/product/' + product.id,
                                type: 'delete',
                                dataType: 'json',
                                statusCode: {
                                    204: function (response) {
                                        products.ajax.reload();
                                        Note.show('编辑成功');
                                    }
                                }
                            });
                        } else {
                            Note.show('已放弃删除');
                        }
                    })
                }
            },
            success: {
                label: '保存编辑',
                className: 'btn-success',
                callback: function () {
                    $.ajax({
                        url: '/api/v1/product/' + product.id,
                        type: 'PUT',
                        dataType: 'json',
                        data: $('#form_edit_product').serialize(),
                        statusCode: {
                            204: function (response) {
                                products.ajax.reload();
                                Note.show('编辑成功');
                            }
                        }
                    });

                }
            }
        }
    });
    $('.clean-input').on('click', function () {
        var field = $(this).attr('f');
        $('#' + field).val('');
    });
}
