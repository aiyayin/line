(self.webpackChunkmuwf = self.webpackChunkmuwf || []).push([
    [8154, 759, 5755, 496], {
        8154: (t, e, n) => {
            "use strict";
            n.d(e, { ZP: () => h, GU: () => o });

            function o(t) { this.judgeVersion("7.13.0") && this.bridge.postMessage({ apiVer: "7.13.0", data: { channel: t.channel }, name: "biz.login", callback: t.callback }) }

            function i(t, e) { if (!(t instanceof e)) throw new TypeError("Cannot call a class as a function") }

            function r(t, e) {
                for (var n = 0; n < e.length; n++) {
                    var o = e[n];
                    o.enumerable = o.enumerable || !1, o.configurable = !0, "value" in o && (o.writable = !0), Object.defineProperty(t, o.key, o)
                }
            }

            function a(t, e, n) { return e && r(t.prototype, e), n && r(t, n), t }

            function s() { return (s = Object.assign || function(t) { for (var e = 1; e < arguments.length; e++) { var n = arguments[e]; for (var o in n) Object.prototype.hasOwnProperty.call(n, o) && (t[o] = n[o]) } return t }).apply(this, arguments) }

            function u(t, e) {
                return function(t) { if (Array.isArray(t)) return t }(t) || function(t, e) {
                    if (!(Symbol.iterator in Object(t)) && "[object Arguments]" !== Object.prototype.toString.call(t)) return;
                    var n = [],
                        o = !0,
                        i = !1,
                        r = void 0;
                    try { for (var a, s = t[Symbol.iterator](); !(o = (a = s.next()).done) && (n.push(a.value), !e || n.length !== e); o = !0); } catch (t) { i = !0, r = t } finally { try { o || null == s.return || s.return() } finally { if (i) throw r } }
                    return n
                }(t, e) || function() { throw new TypeError("Invalid attempt to destructure non-iterable instance") }()
            }
            var c = function() {
                function t(e) { i(this, t), this.appVer = e.version, this.appOs = e.os, this.methodSet = new Set }
                return a(t, [{
                    key: "judgeVersion",
                    value: function(t) {
                        var e = t.split(".").map((function(t) { return parseInt(t) })),
                            n = this.appVer.split(".").map((function(t) { return parseInt(t) })),
                            o = !1;
                        return (n[0] > e[0] || n[0] === e[0] && (n[1] > e[1] || n[1] === e[1] && n[2] >= e[2])) && (o = !0), o
                    }
                }, {
                    key: "postMessage",
                    value: function(t) {
                        var e = t.apiVer,
                            n = t.data,
                            o = void 0 === n ? {} : n,
                            i = t.name,
                            r = t.callback,
                            a = t.errCb,
                            s = void 0 === a ? function(t) { return console.log(t) } : a;
                        if (this.judgeVersion(e)) {
                            for (var u = parseInt(1e8 * Math.random()); this.methodSet.has(u);) u = parseInt(1e8 * Math.random());
                            this.methodSet.add(u);
                            var c, p, l, h = { data: o, name: i };
                            r && (h.callback = (c = u, p = r, l = s, window["method".concat(c)] = function(t) {
                                var e = t;
                                try { e = JSON.parse(t) } catch (t) { console.log(t) }
                                200 === e.status ? p(e.data) : l(e)
                            }, "method".concat(c)));
                            try { "android" === this.appOs && window.T8TJSbridge ? T8TJSbridge.postMessage(JSON.stringify(h)) : "ios" === this.appOs && window.webkit && window.webkit.messageHandlers.T8TJSbridge.postMessage(h) } catch (t) { console.log(t) }
                        }
                    }
                }]), t
            }();

            function p(t) { for (var e = [], n = Object.keys(t), o = 0; o < n.length; o++) e.push([n[o], t[n[o]]]); return e }

            function l(t) { var e = ""; try { e = decodeURIComponent(t) } catch (n) { e = t } return e }
            const h = function() {
                function t() {
                    var e = this;
                    i(this, t), this.npmver = "1.2.12", s(this, function() {
                        var t = (arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : window.location.href).split("?")[1] || "",
                            e = t.substr(0).split("&"),
                            n = {};
                        if (t.length > 0 && e.length > 0)
                            for (var o = 0; o < e.length; o++) {
                                var i = e[o].split("="),
                                    r = i[0],
                                    a = l(i[1]).split("#")[0];
                                n[r] = a
                            }
                        return n
                    }()), this.isApp = parseInt(this.appostype) > 0, this.appOSType = this._getAppOS(), this.appver = this._getAppVersion(), this.isLogin = Boolean(this.to8to_token), this.bridge = new c({ version: this.appver, os: this.appOSType }), this.fnIsLogin = function() { return new Promise((function(t, n) { e.bridge.postMessage({ apiVer: "7.12.0", name: "biz.getUserInfo", callback: function(n) { n.token ? (e.syncLogin(n), t(!0)) : t(!1) } }) })) }, this.noop = function() {}
                }
                return a(t, [{ key: "syncLogin", value: function(t) { this.isLogin = !0, this.to8to_token = t.token, this.uid = t.uid } }, { key: "appExec", value: function(t, e) { "android" === this.appOSType && "undefined" != typeof android ? android.androidFun(JSON.stringify(t)) : "ios" === this.appOSType && (e ? this.iosRequest("to8to://mobileapi.to8to.com/index.php?".concat(JSON.stringify(t))) : window.location.href = "to8to://mobileapi.to8to.com/index.php?".concat(JSON.stringify(t))) } }, {
                    key: "judgeVersion",
                    value: function(t) {
                        var e = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : function() { var t = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : {}; return console.log(t) },
                            n = t.split(".").map((function(t) { return parseInt(t) })),
                            o = this.appver.split(".").map((function(t) { return parseInt(t) })),
                            i = !1;
                        return (o[0] > n[0] || o[0] === n[0] && (o[1] > n[1] || o[1] === n[1] && o[2] >= n[2])) && (i = !0), i || e(), i
                    }
                }, { key: "appOpenView", value: function(t, e) { this.tbtRouter({ suppVer: "7.5.0", path: "/common/webview", query: { url: encodeURIComponent(t) }, errCb: e }) } }, {
                    key: "iosRequest",
                    value: function(t) {
                        var e = document.createElement("iframe");
                        e.setAttribute("src", t), e.style.display = "none", document.body.appendChild(e), setTimeout((function() { document.body.removeChild(e) }), 0)
                    }
                }, {
                    key: "tbtRouter",
                    value: function(t) {
                        for (var e = t.suppVer, n = t.path, o = t.errCb, i = t.query, r = [], a = 0, s = p(void 0 === i ? {} : i); a < s.length; a++) {
                            var c = u(s[a], 2),
                                l = c[0],
                                h = c[1];
                            r.push("".concat(l, "=").concat(h))
                        }
                        var f = "to8to://tbtrouter".concat(n, "?").concat(r.join("&"));
                        this.judgeVersion(e, o) && this.iosRequest(f)
                    }
                }, {
                    key: "use",
                    value: function(t) {
                        for (var e = 0, n = p(t); e < n.length; e++) {
                            var o = u(n[e], 2),
                                i = o[0],
                                r = o[1];
                            this._def(i, r)
                        }
                    }
                }, { key: "_getAppVersion", value: function() { try { var t = this.appversion; return /^\d+\.\d+\.\d+$/.test(t) ? t : "0.0.0" } catch (t) { return console.log(t), "0.0.0" } } }, { key: "_getAppOS", value: function() { return ["", "android", "ios"][parseInt(this.appostype) || 0] } }, { key: "_def", value: function(t, e) { Object.defineProperty(this, t, { value: e, enumerable: !0, writable: !1, configurable: !0 }) } }]), t
            }()
        }
    }
]);