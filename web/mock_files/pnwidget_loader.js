'use strict'

window.pnwidget = {
  path: '',
  styles: ['/static/css/pnwidget_loader.css'],
  stylesReady: false,
  initParams: false,
  // vars for pn17
  userCartEmpty: null,
  paySessionEnd: false,
  admissionPopup: false,
  stickyToggled: null,
  finishOrderFunc: null,
  _extends: Object.assign || function (target) {
    for (var i = 1; i < arguments.length; i++) {
      var source = arguments[i]
      for (var key in source) {
        if (Object.prototype.hasOwnProperty.call(source, key)) {
          target[key] = source[key]
        }
      }
    } return target
  },
  _setPath: function _setPath () {
    var parent_host = window.location.hostname
    if (parent_host.indexOf('pn17-dev2.cultserv.ru') >= 0 || parent_host.indexOf('pn17.cultserv.ru') >= 0) {
      return '//widget3-dev.cultserv.ru'
    }

    if (parent_host.indexOf('spb.ponominalu.ru') >= 0) {
      return '//widget3.cultserv.ru'
    }

    if (/.+ponominalu/.test(parent_host)) {
      return '//widget3-regions.cultserv.ru'
    }

    if (parent_host.indexOf('localhost') >= 0) {
      return 'http://localhost:8080'
    }

    return '//widget3.cultserv.ru'
  },
  _isMobile: function _isMobile () {
    return navigator.userAgent.match(/Android|iPhone|iPad|iPod|Opera Mini|IEMobile/i)
  },
  _getCookie: function _getCookie (name) {
    var matches = document.cookie.match(new RegExp('(?:^|; )' + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + '=([^;]*)'))
    return matches ? decodeURIComponent(matches[1]) : undefined
  },

  loadStyles: function loadStyles () {
    var _this = this

    if (this.styles.length > 0) {
      this.styles.forEach(function (style) {
        var s = document.createElement('link')
        s.href = _this.path + style
        s.type = 'text/css'
        s.charset = 'UTF-8'
        s.rel = 'stylesheet'
        s.onload = function () {
          _this.stylesReady = true
        }
        s.onerror = function () {
          console.error('ERROR:' + s.href + ' load fail')
        }

        document.head.appendChild(s)
      })
    }
  },
  init: function init (options) {
    this.path = this._setPath()
    this.options = options
    if (options.customStyle) {
      this.loadStyles()
    }
    // listen toggle check menu for hide platform close button, send coupon
    var listener = function listener (event) {
      var iframe = document.querySelector('iframe#pn-widget-iframe')
      var widgetInPopup = document.querySelector('#pnWidgetOverlay')
      var closeWidgetButton = document.querySelector('#pnWidgetCloseButton')
      var pnWidgetCloseButton = document.querySelector('.pn-widget-close-button')
      if (iframe) {
        if (typeof event.data.stickyToggled !== 'undefined') {
          this.stickyToggled = event.data.stickyToggled
        }

        // listen cart for tickets
        if (typeof event.data.emptyCart !== 'undefined') {
          this.userCartEmpty = event.data.emptyCart
        }

        if (typeof event.data.paySessionEnd !== 'undefined') {
          this.paySessionEnd = event.data.paySessionEnd
        }

        if (typeof event.data.admissionPopup !== 'undefined') {
          if (closeWidgetButton) {
            if (event.data.admissionPopup) {
              closeWidgetButton.style.display = 'none'
            } else {
              closeWidgetButton.style.display = 'flex'
            }
          }
          this.admissionPopup = event.data.admissionPopup
        }

        if (typeof event.data.justPopUp !== 'undefined') {
          if (closeWidgetButton) {
            if (event.data.justPopUp) {
              closeWidgetButton.style.display = 'none'
            } else {
              closeWidgetButton.style.display = 'flex'
            }
          }
          if (pnWidgetCloseButton) {
            if (event.data.justPopUp) {
              pnWidgetCloseButton.style.display = 'none'
            } else {
              pnWidgetCloseButton.style.display = null
            }            
          }
        }

        if (typeof event.data.getCoupon !== 'undefined') {
          if (event.data.getCoupon) {
            try {
              var coupon = sessionStorage.getItem('pn-coupon')
              if (coupon) {
                iframe.contentWindow.postMessage({ coupon: coupon }, '*')
              }
            } catch (error) {
              console.error('ERROR: ', error)
            }
          }
        }

        if (typeof event.data.getHref !== 'undefined') {
          if (event.data.getHref) {
            var pnData = {
              href: window.location.href,
              hostname: window.location.hostname,
              ga_cid: this._getCookie('_ga_cid'),
              last_utm_source: this._getCookie('last_utm_source'),
              advcake_trackid: this._getCookie('advcake_trackid'),
              user_unic_ac_id: this._getCookie('user_unic_ac_id')
            }
            if (window.PN_WIDGET && window.PN_WIDGET.parentWindowHref) {
              pnData.href = window.PN_WIDGET.parentWindowHref
            }
            iframe.contentWindow.postMessage({ pnData: pnData }, '*')
          }
        }

        if (typeof event.data.finishOrder !== 'undefined') {
          if (this.finishOrderFunc && typeof this.finishOrderFunc === 'function') {
            this.finishOrderFunc(event.data.finishOrder)
          }
        }

        if (widgetInPopup) {
          iframe.contentWindow.postMessage({isPopupModal: true}, '*')
        }
      }
    }.bind(this)
    // listen window resize
    var resize = function resize () {
      var iframe = document.querySelector('iframe#pn-widget-iframe')
      setTimeout(function () {
        if (iframe) {
          var width = window.parent.innerWidth
          iframe.contentWindow.postMessage({ innerWidth: width }, '*')
        }
      }, 300)
    }
    if (window.addEventListener) {
      window.addEventListener('message', listener)
      window.addEventListener('resize', resize)
    } else {
      // IE8
      window.attachEvent('onmessage', listener)
    }
  },
  show: function show (options, func) {
    var _this2 = this
    var opt = _this2._extends({}, this.options)
    _this2._extends(opt, options) // overriding options previously defined.

    if (window.location.hostname.indexOf('mts.') >= 0) {
      opt.isMTS = true // отметка запущен ли сайт на поддомене МТС
    }

    if (func && typeof func === 'function') {
      _this2.finishOrderFunc = func
    } else {
      _this2.finishOrderFunc = null
    }

    this.pnwidget_iframe = document.createElement('iframe')
    this.pnwidget_iframe.src = this.path + '/index.html?options_json=' + JSON.stringify(opt)
    this.pnwidget_iframe.style.position = 'relative'
    this.pnwidget_iframe.id = 'pn-widget-iframe'

    if (window.PN_WIDGET && window.PN_WIDGET.parentWindowParams) {
      this.pnwidget_iframe.src += '&' + window.PN_WIDGET.parentWindowParams
    } else if (window.location.search.substr(1).indexOf('utm') !== -1) {
      this.pnwidget_iframe.src += '&' + window.location.search.substr(1)
    }

    if (opt.inline) {
      document.write('<iframe id="pn-widget-iframe" style="border: none; width: 100%; height: 100vh; position: relative;" src="' + this.pnwidget_iframe.src + '"></iframe>')
      return
    } else if (opt.containerId) {
      this.pnwidget_iframe.style.width = '100%'
      this.pnwidget_iframe.style.height = '100%'
      this.pnwidget_iframe.style.border = 'none'
      this.pnwidget_iframe.style.overflow = 'auto'
      this.widgetContainer = document.getElementById(opt.containerId)
    } else {
      if (_this2._isMobile()) {
        window.open(this.pnwidget_iframe.src)
        return
      }
      this.widgetOverlay = document.createElement('div')
      this.widgetOverlay.setAttribute('id', 'pnWidgetOverlay')
      this.widgetOverlay.style['z-index'] = 99999
      document.body.appendChild(this.widgetOverlay)

      this.widgetContainer = document.createElement('div')
      this.widgetContainer.setAttribute('id', 'pnWidgetContainer')
      this.widgetOverlay.appendChild(this.widgetContainer)

      if (opt.closeButton) {
        this.closeButton = document.createElement('div')
        this.closeButton.innerHTML = '&times;'
        this.closeButton.setAttribute('id', 'pnWidgetCloseButton')
        this.widgetContainer.appendChild(this.closeButton)

        this.closeButton.addEventListener('click', function () {
          if (_this2.options.hideCloseConfirm) {
            _this2.hide()
          } else {
            _this2.showCloseConfirm()
          }
        })
      }

      this.widgetOverlay.addEventListener('click', function (e) {
        if (!_this2.options.hideCloseConfirm) {
          _this2.showCloseConfirm()
        }
      })
    }

    this.widgetContainer.appendChild(this.pnwidget_iframe)
  },
  hide: function hide () {
    document.body.removeChild(this.widgetOverlay) // todo: удаляет , но в консоли ругается, поправить
    delete this.widgetOverlay
  },
  showCloseConfirm: function showCloseConfirm () {
    var _this3 = this

    if (document.getElementById('pnWidgetCloseConfirm')) {
      return
    }

    this.closeConfirmOverlay = document.createElement('div')
    this.closeConfirmOverlay.setAttribute('id', 'pnWidgetCloseConfirmOverlay')
    this.closeConfirm = document.createElement('div')
    this.closeConfirm.setAttribute('id', 'pnWidgetCloseConfirm')
    this.closeConfirm.style['z-index'] = 99999
    this.closeConfirm.innerHTML = '<div class="pnWidgetCloseConfirm__body">Вы точно хотите прекратить покупку билетов?</div>' + '<div class="pnWidgetCloseConfirm__footer">' + '<button id="pnButtonCloseWidget" class="pnWidgetCloseConfirm__button">Да</button>' + '<button id="pnButtonCloseConfirm" class="pnWidgetCloseConfirm__button pnWidgetCloseConfirm__button--red">Нет, не хочу</button>' + '</div>'
    document.body.appendChild(this.closeConfirmOverlay)
    document.body.appendChild(this.closeConfirm)

    document.getElementById('pnButtonCloseWidget').addEventListener('click', function (e) {
      _this3.hide()
      document.body.removeChild(_this3.closeConfirmOverlay)
      document.body.removeChild(_this3.closeConfirm)
    })
    document.getElementById('pnButtonCloseConfirm').addEventListener('click', function (e) {
      document.body.removeChild(_this3.closeConfirmOverlay)
      document.body.removeChild(_this3.closeConfirm)
    })
  }
}
