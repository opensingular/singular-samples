;(function () {
    "use strict";
    if (window.substringMatcher == undefined) {
        window.substringMatcher = function (value_list) {
            var clearText = function (x) {
                return S(x).latinise().s;
            };
            return function findMatches(q, cb) {
                var matches = [];
                var substrRegex = new RegExp(clearText(q), 'i');
                $.each(value_list, function (i, value) {
                    if (substrRegex.test(clearText(value['value']))) {
                        matches.push(value);
                    }
                });
                cb(matches);
            };
        };
    }
}());

(function ($) {
    "use strict";
    window.SingularTypeahead = {
        configure: function (container, valueField) {

            var clear =
                    "<a id='" + container + "_clear' style='position:absolute;top:8px;right:10px;'>" +
                    "   <span class='glyphicon glyphicon-remove tt-clear-icon'></span>" +
                    "</a>",
                typeaheadField = '#' + container + ' > span  > input',
                $typeaheadField = $(typeaheadField);

            function updateValue(newValue) {
                var $valueField = $('#' + valueField);
                $valueField.val(newValue);
                $valueField.trigger('change');
            }

            function onClear() {
                var _$typeaheadField = $(typeaheadField);
                $(this).remove();
                _$typeaheadField.typeahead('val', '');
                _$typeaheadField.removeAttr('readonly');
                updateValue('');
            }

            function triggerKeydown(component, keyCode) {
                var e = $.Event('keydown');
                e.keyCode = e.which = keyCode;
                $(component).trigger(e);
            }

            function focusNextComponent(keydownEvent) {
                var focusables = $(":focusable");
                $(focusables[focusables.index($(typeaheadField)) + (keydownEvent.shiftKey ? -1 : 1)]).focus();
            }

            function appendClearButton() {
                var _$typeaheadField = $(typeaheadField);
                _$typeaheadField.attr('readonly', true);
                _$typeaheadField.after(clear);
                $('#' + container + '_clear').on('click', onClear);
                _$typeaheadField.blur();
            }

            $typeaheadField.on('typeahead:selected', function (event, selection) {
                var _$typeaheadField = $(typeaheadField);
                updateValue(selection.key);
                appendClearButton();
                _$typeaheadField.blur();
            });

            $typeaheadField.on('keydown', function (keydownEvent) {
                var code = keydownEvent.keyCode || keydownEvent.which;
                if (code === 9) {
                    if ($(this).val()) {
                        keydownEvent.preventDefault();
                        triggerKeydown(this, 40);
                        triggerKeydown(this, 13);
                        focusNextComponent(keydownEvent);
                    }
                }
            });

            if ($typeaheadField.val()) {
                appendClearButton();
            }
        }
    };
}(jQuery));