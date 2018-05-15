[![Clojars Project](https://img.shields.io/clojars/v/re-frame-bootstrap-toggle.svg)](https://clojars.org/re-frame-bootstrap-toggle)

# re-frame Bootstrap Toggle
Bootstrap Toggle is a flexible Bootstrap plugin that converts checkboxes into toggles. This is fully reactive rewrite of the JavaScript part for [re-frame]. It doesn't use checkboxes and of course no jQuery! And actually, it doesn't even depend on re-frame, it should work in any [Reagent] app.

Visit http://www.bootstraptoggle.com for demos of the abilities.

## Getting Started

### Installation

Add this to your dependencies:
```cljs
[re-frame-bootstrap-toggle "0.1.1"]
```

Also, you need to include a stylesheet from the [css directory](/css).

## Usage

```cljs
(ns your.re-frame.app
  (:require [re-frame.core :refer [subscribe dispatch]]
            [re-frame-bootstrap-toggle.core :refer [toggle]]))

(defn view []
  (let [state (subscribe [:toggle-state])]
    (fn []
      [toggle {:checked @toggle-state
               :on-click #(dispatch [:toggle-the-toggle])}])))
```

### Options

You can pass additional options along with `:checked` and `:on-click`:

Name|Type|Default|Description|
---|---|---|---
`:on`|string or [hiccup]|`"On"`|Label of the on toggle
`:off`|string or [hiccup]|`"Off"`|Label of the off toggle
`:onstyle`|string|`"primary"`|Style of the on toggle. Possible values are the [button classes][bootstrap-buttons] without the leading `btn-`, such as `"info"` or `"warning"`
`:offstyle`|string|`"default"`|Same as `:onstyle`, but for the off toggle
`:width`|integer|`nil`|Sets the width of the toggle. If set to `nil`, width will be calculated.
`:height`|integer|`nil`|Sets the height of the toggle. If set to `nil`, height will be calculated.

The following options **are not yet supported**, but patches are welcome!

Name|Type|Default|Description|
---|---|---|---
`:size`|string|`"normal"`|Size of the toggle. Possible values are `"large"`, `"normal"`, `"small"`, `"mini"`.
`:style`|string| |Appends the value to the class attribute of the toggle. This can be used to apply custom styles. Refer to Custom Styles for reference.

### But I need the checkbox!

While the checkbox introduces some state issues, it's no biggy to just add them yourself in case you want to submit the state in a form. You probably want to add a hidden field.
```cljs
(defn view []
  (let [state (subscribe [:toggle-state])]
    (fn []
      [:div
        [toggle {:checked @toggle-state
                 :on-click #(dispatch [:toggle-the-toggle])}]
        [:input {:type "hidden"
                 :name "toggle"
                 :value (if @toggle-state "on" "off")}])))
```

### Usage with Bootstrap 4

We use Bootstrap 3.3, but it seems pretty strait forward to use with Bootstrap 4 as well. First you have to set the `:offstyle`, since the `btn-default` css class was renamed to `btn-secondary`. Then you need to include the css rules mentioned in [this GitHub issue][issue-186].

[re-frame]: https://github.com/Day8/re-frame
[Reagent]: http://reagent-project.github.io/
[hiccup]: https://github.com/weavejester/hiccup/wiki/Syntax
[bootstrap-buttons]: https://getbootstrap.com/docs/3.3/css/#buttons-options
[issue-186]: https://github.com/minhur/bootstrap-toggle/issues/186
