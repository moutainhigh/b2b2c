/*   
JavaScript Name: ShopNC-B2B2C-Java版admincp全局方法JS
Version: 1.0.0
*/

var handleSlimScroll = function() {
    "use strict";
    $("[data-scrollbar=true]").each(function() {
        generateSlimScroll($(this))
    })
};
var generateSlimScroll = function(e) {
    var t = $(e).attr("data-height");
    t = !t ? $(e).height() : t;
    var n = {
        height: t,
        alwaysVisible: true
    };
    if (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)) {
        n.wheelStep = 3;
        n.touchScrollStep = 100
    }
    $(e).slimScroll(n)
};
var handleSidebarMenu = function() {
    "use strict";
    $(".sidebar .nav > .has-sub > a").click(function() {
        var e = $(this).next(".sub-menu");
        var t = ".sidebar .nav > li.has-sub > .sub-menu";
        if ($(".page-sidebar-minified").length === 0) {
            $(t).not(e).slideUp(250,
            function() {
                $(this).closest("li").removeClass("expand")
            });
            $(e).slideToggle(250,
            function() {
                var e = $(this).closest("li");
                if ($(e).hasClass("expand")) {
                    $(e).removeClass("expand")
                } else {
                    $(e).addClass("expand")
                }
            })
        }
    });
    $(".sidebar .nav > .has-sub .sub-menu li.has-sub > a").click(function() {
        if ($(".page-sidebar-minified").length === 0) {
            var e = $(this).next(".sub-menu");
            $(e).slideToggle(250)
        }
    })
};
var handleMobileSidebarToggle = function() {
    var e = false;
    $(".sidebar").on("click touchstart",
    function(t) {
        if ($(t.target).closest(".sidebar").length !== 0) {
            e = true
        } else {
            e = false;
            t.stopPropagation()
        }
    });
    $(document).on("click touchstart",
    function(t) {
        if ($(t.target).closest(".sidebar").length === 0) {
            e = false
        }
        if (!t.isPropagationStopped() && e !== true) {
            if ($("#page-container").hasClass("page-sidebar-toggled")) {
                $("#page-container").removeClass("page-sidebar-toggled")
            }
            if ($(window).width() < 979) {
                if ($("#page-container").hasClass("page-with-two-sidebar")) {
                    $("#page-container").removeClass("page-right-sidebar-toggled")
                }
            }
        }
    });
    $("[data-click=right-sidebar-toggled]").click(function(e) {
        e.stopPropagation();
        var t = "#page-container";
        var n = "page-right-sidebar-collapsed";
        n = $(window).width() < 979 ? "page-right-sidebar-toggled" : n;
        if ($(t).hasClass(n)) {
            $(t).removeClass(n)
        } else {
            $(t).addClass(n)
        }
        if ($(window).width() < 480) {
            $("#page-container").removeClass("page-sidebar-toggled")
        }
    });
    $("[data-click=sidebar-toggled]").click(function(e) {
        e.stopPropagation();
        var t = "page-sidebar-toggled";
        var n = "#page-container";
        if ($(n).hasClass(t)) {
            $(n).removeClass(t)
        } else {
            $(n).addClass(t)
        }
        if ($(window).width() < 480) {
            $("#page-container").removeClass("page-right-sidebar-toggled")
        }
    })
};
var handleSidebarMinify = function() {
    $("[data-click=sidebar-minify]").click(function(e) {
        e.preventDefault();
        var t = "page-sidebar-minified";
        var n = "#page-container";
        if ($(n).hasClass(t)) {
            $(n).removeClass(t);
            if ($(n).hasClass("page-sidebar-fixed")) {
                generateSlimScroll($('#sidebar [data-scrollbar="true"]'))
            }
        } else {
            $(n).addClass(t);
            if ($(n).hasClass("page-sidebar-fixed")) {
                $('#sidebar [data-scrollbar="true"]').slimScroll({
                    destroy: true
                });
                $('#sidebar [data-scrollbar="true"]').removeAttr("style")
            }
            $("#sidebar [data-scrollbar=true]").trigger("mouseover")
        }
        $(window).trigger("resize")
    })
};
var handlePageContentView = function() {
    "use strict";
    $.when($("#page-loader").addClass("hide")).done(function() {
        $("#page-container").addClass("in")
    })
};
var handlePanelAction = function() {
    "use strict";
    $("[data-click=panel-remove]").hover(function() {
        $(this).tooltip({
            title: "Remove",
            placement: "bottom",
            trigger: "hover",
            container: "body"
        });
        $(this).tooltip("show")
    });
    $("[data-click=panel-remove]").click(function(e) {
        e.preventDefault();
        $(this).tooltip("destroy");
        $(this).closest(".panel").移除()
    });
    $("[data-click=panel-collapse]").hover(function() {
        $(this).tooltip({
            title: "折叠 / 展开",
            placement: "bottom",
            trigger: "hover",
            container: "body"
        });
        $(this).tooltip("show")
    });
    $("[data-click=panel-collapse]").click(function(e) {
        e.preventDefault();
        $(this).closest(".panel").find(".panel-body").slideToggle()
    });
    $("[data-click=panel-reload]").hover(function() {
        $(this).tooltip({
            title: "刷新",
            placement: "bottom",
            trigger: "hover",
            container: "body"
        });
        $(this).tooltip("show")
    });
    $("[data-click=panel-reload]").click(function(e) {
        e.preventDefault();
        var t = $(this).closest(".panel");
        if (!$(t).hasClass("panel-loading")) {
            var n = $(t).find(".panel-body");
            var r = '<div class="panel-loader"><span class="spinner-small"></span></div>';
            $(t).addClass("panel-loading");
            $(n).prepend(r);
            setTimeout(function() {
                $(t).removeClass("panel-loading");
                $(t).find(".panel-loader").remove()
            },
            2e3)
        }
    });
    $("[data-click=panel-expand]").hover(function() {
        $(this).tooltip({
            title: "最大化",
            placement: "bottom",
            trigger: "hover",
            container: "body"
        });
        $(this).tooltip("show")
    });
    $("[data-click=panel-expand]").click(function(e) {
        e.preventDefault();
        var t = $(this).closest(".panel");
        var n = $(t).find(".panel-body");
        var r = 40;
        if ($(n).length !== 0) {
            var i = $(t).offset().top;
            var s = $(n).offset().top;
            r = s - i
        }
        if ($("body").hasClass("panel-expand") && $(t).hasClass("panel-expand")) {
            $("body, .panel").removeClass("panel-expand");
            $(".panel").removeAttr("style");
            $(n).removeAttr("style")
        } else {
            $("body").addClass("panel-expand");
            $(this).closest(".panel").addClass("panel-expand");
            if ($(n).length !== 0 && r != 40) {
                var o = 40;
                $(t).find(" > *").each(function() {
                    var e = $(this).attr("class");
                    if (e != "panel-heading" && e != "panel-body") {
                        o += $(this).height() + 30
                    }
                });
                if (o != 40) {
                    $(n).css("top", o + "px")
                }
            }
        }
        $(window).trigger("resize")
    })
};
var handleDraggablePanel = function() {
    "use strict";
    var e = $(".panel").parent("[class*=col]");
    var t = ".panel-heading";
    var n = ".row > [class*=col]";
    $(e).sortable({
        handle: t,
        connectWith: n,
        stop: function(e, t) {
            t.item.find(".panel-title").append('<i class="fa fa-refresh fa-spin m-l-5" data-id="title-spinner"></i>');
            handleSavePanelPosition(t.item)
        }
    })
};
var handelTooltipPopoverActivation = function() {
    "use strict";
    $("[data-tip=tooltip]").tooltip();
    $("[data-toggle=popover]").popover()
};
var handleScrollToTopButton = function() {
    "use strict";
    $(document).scroll(function() {
        var e = $(document).scrollTop();
        if (e >= 200) {
            $("[data-click=scroll-top]").addClass("in")
        } else {
            $("[data-click=scroll-top]").removeClass("in")
        }
    });
    $("[data-click=scroll-top]").click(function(e) {
        e.preventDefault();
        $("html, body").animate({
            scrollTop: $("body").offset().top
        },
        500)
    })
};
var handleThemePageStructureControl = function() {
    if ($.cookie && $.cookie("theme")) {
        if ($(".theme-list").length !== 0) {
            $(".theme-list [data-theme]").closest("li").removeClass("active");
            $('.theme-list [data-theme="' + $.cookie("theme") + '"]').closest("li").addClass("active")
        }
        var e = CSS_SITE_URL+"/theme/" + $.cookie("theme") + ".css";
        $("#theme").attr("href", e)
    }
    if ($.cookie && $.cookie("sidebar-styling")) {
        if ($(".sidebar").length !== 0 && $.cookie("sidebar-styling") == "grid") {
            $(".sidebar").addClass("sidebar-grid");
            $('[name=sidebar-styling] option[value="2"]').prop("selected", true)
        }
    }
    if ($.cookie && $.cookie("header-styling")) {
        if ($(".header").length !== 0 && $.cookie("header-styling") == "navbar-inverse") {
            $(".header").addClass("navbar-inverse");
            $('[name=header-styling] option[value="2"]').prop("selected", true)
        }
    }
    if ($.cookie && $.cookie("content-gradient")) {
        if ($("#page-container").length !== 0 && $.cookie("content-gradient") == "enabled") {
            $("#page-container").addClass("gradient-enabled");
            $('[name=content-gradient] option[value="2"]').prop("selected", true)
        }
    }
    if ($.cookie && $.cookie("content-styling")) {
        if ($("body").length !== 0 && $.cookie("content-styling") == "black") {
            $("body").addClass("flat-black");
            $('[name=content-styling] option[value="2"]').prop("selected", true)
        }
    }
    $(".theme-list [data-theme]").live("click",
    function() {
        var e = CSS_SITE_URL+"/theme/" + $(this).attr("data-theme") + ".css";
        $("#theme").attr("href", e);
        $(".theme-list [data-theme]").not(this).closest("li").removeClass("active");
        $(this).closest("li").addClass("active");
        $.cookie("theme", $(this).attr("data-theme"))
    });
    $(".theme-panel [name=header-styling]").live("change",
    function() {
        var e = $(this).val() == 1 ? "navbar-default" : "navbar-inverse";
        var t = $(this).val() == 1 ? "navbar-inverse" : "navbar-default";
        $("#header").removeClass(t).addClass(e);
        $.cookie("header-styling", e)
    });
    $(".theme-panel [name=sidebar-styling]").live("change",
    function() {
        if ($(this).val() == 2) {
            $("#sidebar").addClass("sidebar-grid");
            $.cookie("sidebar-styling", "grid")
        } else {
            $("#sidebar").removeClass("sidebar-grid");
            $.cookie("sidebar-styling", "default")
        }
    });
    $(".theme-panel [name=content-gradient]").live("change",
    function() {
        if ($(this).val() == 2) {
            $("#page-container").addClass("gradient-enabled");
            $.cookie("content-gradient", "enabled")
        } else {
            $("#page-container").removeClass("gradient-enabled");
            $.cookie("content-gradient", "disabled")
        }
    });
    $(".theme-panel [name=content-styling]").live("change",
    function() {
        if ($(this).val() == 2) {
            $("body").addClass("flat-black");
            $.cookie("content-styling", "black")
        } else {
            $("body").removeClass("flat-black");
            $.cookie("content-styling", "default")
        }
    });
    $(".theme-panel [name=sidebar-fixed]").live("change",
    function() {
        if ($(this).val() == 1) {
            if ($(".theme-panel [name=header-fixed]").val() == 2) {
                alert("Default Header with Fixed Sidebar option is not supported. Proceed with Fixed Header with Fixed Sidebar.");
                $('.theme-panel [name=header-fixed] option[value="1"]').prop("selected", true);
                $("#header").addClass("navbar-fixed-top");
                $("#page-container").addClass("page-header-fixed")
            }
            $("#page-container").addClass("page-sidebar-fixed");
            if (!$("#page-container").hasClass("page-sidebar-minified")) {
                generateSlimScroll($('.sidebar [data-scrollbar="true"]'))
            }
        } else {
            $("#page-container").removeClass("page-sidebar-fixed");
            if ($(".sidebar .slimScrollDiv").length !== 0) {
                if ($(window).width() <= 979) {
                    $(".sidebar").each(function() {
                        if (!($("#page-container").hasClass("page-with-two-sidebar") && $(this).hasClass("sidebar-right"))) {
                            $(this).find(".slimScrollBar").remove();
                            $(this).find(".slimScrollRail").remove();
                            $(this).find('[data-scrollbar="true"]').removeAttr("style");
                            var e = $(this).find('[data-scrollbar="true"]').parent();
                            var t = $(e).html();
                            $(e).replaceWith(t)
                        }
                    })
                } else if ($(window).width() > 979) {
                    $('.sidebar [data-scrollbar="true"]').slimScroll({
                        destroy: true
                    });
                    $('.sidebar [data-scrollbar="true"]').removeAttr("style")
                }
            }
            if ($("#page-container .sidebar-bg").length === 0) {
                $("#page-container").append('<div class="sidebar-bg"></div>')
            }
        }
    });
    $(".theme-panel [name=header-fixed]").live("change",
    function() {
        if ($(this).val() == 1) {
            $("#header").addClass("navbar-fixed-top");
            $("#page-container").addClass("page-header-fixed");
            $.cookie("header-fixed", true)
        } else {
            if ($(".theme-panel [name=sidebar-fixed]").val() == 1) {
                alert("不支持默认头部与固定侧边栏选项,请保持现有的默认设置.");
                $('.theme-panel [name=sidebar-fixed] option[value="2"]').prop("selected", true);
                $("#page-container").removeClass("page-sidebar-fixed");
                if ($("#page-container .sidebar-bg").length === 0) {
                    $("#page-container").append('<div class="sidebar-bg"></div>')
                }
            }
            $("#header").removeClass("navbar-fixed-top");
            $("#page-container").removeClass("page-header-fixed");
            $.cookie("header-fixed", false)
        }
    })
};
var handleThemePanelExpand = function() {
    $('[data-click="theme-panel-expand"]').live("click",
    function() {
        var e = ".theme-panel";
        var t = "active";
        if ($(e).hasClass(t)) {
            $(e).removeClass(t)
        } else {
            $(e).addClass(t)
        }
    })
};
var handleAfterPageLoadAddClass = function() {
    if ($("[data-pageload-addclass]").length !== 0) {
        $(window).load(function() {
            $("[data-pageload-addclass]").each(function() {
                var e = $(this).attr("data-pageload-addclass");
                $(this).addClass(e)
            })
        })
    }
};
var handleSavePanelPosition = function(e) {
    "use strict";
    if ($(".ui-sortable").length !== 0) {
        var t = [];
        var n = 0;
        $.when($(".ui-sortable").each(function() {
            var e = $(this).find("[data-sortable-id]");
            if (e.length !== 0) {
                var r = [];
                $(e).each(function() {
                    var e = $(this).attr("data-sortable-id");
                    r.push({
                        id: e
                    })
                });
                t.push(r)
            } else {
                t.push([])
            }
            n++
        })).done(function() {
            var n = window.location.href;
            n = n.split("?");
            n = n[0];
            localStorage.setItem(n, JSON.stringify(t));
            $(e).find('[data-id="title-spinner"]').delay(500).fadeOut(500,
            function() {
                $(this).remove()
            })
        })
    }
};
var handleLocalStorage = function() {
    "use strict";
    if (typeof Storage !== "undefined") {
        var e = window.location.href;
        e = e.split("?");
        e = e[0];
        var t = localStorage.getItem(e);
        if (t) {
            t = JSON.parse(t);
            var n = 0;
            $(".panel").parent('[class*="col-"]').each(function() {
                var e = t[n];
                var r = $(this);
                $.each(e,
                function(e, t) {
                    var n = '[data-sortable-id="' + t.id + '"]';
                    if ($(n).length !== 0) {
                        var i = $(n).clone();
                        $(n).remove();
                        $(r).append(i)
                    }
                });
                n++
            })
        }
    } else {
        alert("Your browser is not supported with the local storage")
    }
};
var handleResetLocalStorage = function() {
    "use strict";
    $("[data-click=reset-local-storage]").live("click",
    function(e) {
        e.preventDefault();
        var t = "" + '<div class="modal fade" data-modal-id="reset-local-storage-confirmation">' + '    <div class="modal-dialog">' + '        <div class="modal-content">' + '            <div class="modal-header">' + '                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>' + '                <h4 class="modal-title"><i class="fa fa-refresh m-r-5"></i> 确认重置页面缓存</h4>' + "            </div>" + '            <div class="modal-body">' + '                <div class="alert alert-info m-b-0">你要重置所有设定的界面风格并更新页面缓存吗?</div>' + "            </div>" + '            <div class="modal-footer">' + '                <a href="javascript:;" class="btn btn-sm btn-white" data-dismiss="modal"><i class="fa fa-close"></i> 放弃</a>' + '                <a href="javascript:;" class="btn btn-sm btn-inverse" data-click="confirm-reset-local-storage"><i class="fa fa-check"></i> 确定</a>' + "            </div>" + "        </div>" + "    </div>" + "</div>";
        $("body").append(t);
        $('[data-modal-id="reset-local-storage-confirmation"]').modal("show")
    });
    $('[data-modal-id="reset-local-storage-confirmation"]').live("hidden.bs.modal",
    function() {
        $('[data-modal-id="reset-local-storage-confirmation"]').remove()
    });
    $("[data-click=confirm-reset-local-storage]").live("click",
    function(e) {
        e.preventDefault();
        var t = window.location.href;
        t = t.split("?");
        t = t[0];
        localStorage.removeItem(t);
        window.location.href = document.URL
    })
};

/**
 * 阻止表单中的搜索的回车提交事件
 * @type {{init}}
 */
var searchPreventDefault = function () {

    $(".search-dtGrid-form input[type=text]").on("keydown",function(e){

        if(e.keyCode ==13){
            e.preventDefault();
            var a = $("#customSearch");
            a.length && a.trigger("click");
        }
    });


};
var App = function() {
    "use strict";
    return {
        init: function() {
            handleDraggablePanel();
            handleLocalStorage();
            handleResetLocalStorage();
            handleSlimScroll();
            handleSidebarMenu();
            handleMobileSidebarToggle();
            handleSidebarMinify();
            handleThemePageStructureControl();
            handleThemePanelExpand();
            handleAfterPageLoadAddClass();
            handlePanelAction();
            handelTooltipPopoverActivation();
            handleScrollToTopButton();
            handlePageContentView();
            searchPreventDefault();
        }
    }
} ()