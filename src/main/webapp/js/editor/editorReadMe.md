<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.js"></script>
<script src="./lib/tui-code-snippet/tui-code-snippet.js"></script>
<script src="./lib/plantuml-encoder/plantuml-encoder.js"></script>
<script src="./lib/raphael/raphael.js"></script>
<script src="./lib/tui-chart/tui-chart.js"></script>
<script src="./lib/tui-color-picker/tui-color-picker.js"></script>

<script src="./lib/markdown-it/markdown-it.js"></script>
<script src="./lib/to-mark/to-mark.js"></script>

<script src="./lib/codemirror/codemirror.js"></script>
<script src="./lib/highlightjs/highlight.pack.js"></script>
<script src="./lib/squire/squire-raw.js"></script>
<link rel="stylesheet" href="./lib/codemirror/codemirror.css">
<link rel="stylesheet" href="./lib/highlightjs/github.css">
</head>
<body>
<script src="./dist/tui-editor-Editor-all.js"></script>
<!-- exts: ['scrollSync'] 이것을 위해 필요 -->
<script src="./dist/tui-editor-extScrollSync.js"></script>
<!-- table위해 필요 -->
<script src="./dist/tui-editor-extTable.js"></script>
<!-- 칼라픽커를 위해 필요 -->
<script src="./dist/tui-editor-extColorSyntax.js"></script>
<link rel="stylesheet" href="./dist/tui-editor.css">
<link rel="stylesheet" href="./dist/tui-editor-contents.css">
<link rel="stylesheet" href="./lib/tui-color-picker/tui-color-picker.css">
<link rel="stylesheet" href="./lib/tui-chart/tui-chart.css">

   <!-- IMPORT Font Awesome 5 here-->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.0/css/all.css" integrity="sha384-lKuwvrZot6UHsBSfcMvOkWwlCMgc0TaWr+30HWe3a4ltaBwTZhyTEggF5tJv8tbt" crossorigin="anonymous">
    <style>
      /* class for our button */
      .our-button-class{
        float: left;
        box-sizing: border-box;
        outline: none;
        cursor: pointer;
        background-color: #fff;
        width: 22px;
        height: 22px;
        border-radius: 0;
        margin: 5px 3px;
        border: 1px solid #fff;
        display: flex;
        justify-content: center;
        align-items: center;
      }
    </style>

<div id="editSection"></div>

<button id="mybutton">Click me to set content programmatically</button>
<div id="editSection2"></div>

<script>
//https://nhnent.github.io/tui.editor/api/latest/ToastUIEditor.html#ToastUIEditor
// height: Height in string or auto ex) 300px | auto
// initialValue: Initial value. Set Markdown string
// initialEditType: Initial type to show markdown | wysiwyg
// previewType: Preview style of Markdown mode tab | vertical
// usageStatistics: Let us know the hostname. We want to learn from you how you are using the editor. You are free to disable it. true | false
// exts 스크롤할 때, 좌우가 같이 움직임

    $('#editSection').tuiEditor({
        initialEditType: 'markdown',
        previewStyle: 'vertical',
        height: '300px',
		exts: ['scrollSync','table','colorSyntax','chart'],
		toolbarItems: [
          'heading',
          'bold',
          'italic',
          'strike',
          'divider',
          'hr',
          'quote',
          'divider',
          'ul',
          'ol',
          'task',
          'indent',
          'outdent',
          'divider',
          'table',
          'image',
          'link',
          'divider',
          'code',
          'codeblock',
		 {
            type: 'button',
            options: {
              $el: $('<div class="our-button-class"><i class="fas fa-briefcase-medical"></i></div>'),
              name: 'test2',
              className: '',
              command: 'Bold', // you can use "Bold"
              tooltip: 'Bold'
            }
          }
        ]
      });
	  var content = `use "editor.setValue" to set content programmatically`;
      var editor = new tui.Editor({
        el: document.querySelector('#editSection2'),
        previewStyle: 'vertical',
        height: '400px',
        initialEditType: 'markdown',
        initialValue: content,
        toolbarItems: [
        'heading',
          'bold',
          'italic',
          'strike',
          'divider',
          'hr',
          'quote',
          'divider',
          'ul',
          'ol',
          'task',
          'indent',
          'outdent',
          'divider',
          'table',
          'image',
          'link',
          'divider',
          'code',
          'codeblock',
          'divider',
          // ADD button method 1
          {
            type: 'button',
            options: {
              $el: $('<div class="our-button-class"><i class="fas fa-briefcase-medical"></i></div>'),
              name: 'test2',
              className: '',
              command: 'Bold', // you can use "Bold"
              tooltip: 'Bold'
            }
          }
        ]
      });

      // ADD button method 2
      const toolbar = editor.getUI().getToolbar();

      editor.eventManager.addEventType('Event1');
      editor.eventManager.listen('Event1', () => {
        alert('button click!');
        // do some other thing...
      });

      toolbar.addButton({
        name: 'customize',
        className: 'fab fa-accessible-icon',
        event: 'Event1',
        tooltip: 'Apple!!!',
        $el: $('<div class="our-button-class"><i class="fab fa-apple"></i></div>')
      }, 1);

	var content = [
        '![image](https://cloud.githubusercontent.com/assets/389021/16107646/9729e556-33d8-11e6-933f-5b09fa3a53bb.png)',
        '# Heading 1',
        '## Heading 2',
        '### Heading 3',
        '#### Heading 4',
        '##### Heading 5',
        '###### Heading 6',
        '    code block',
        '```js',
        'console.log("fenced code block");',
        '```',
        '<pre>**HTML block**</pre>',
        '* list',
        '    * list indented',
        '1. ordered',
        '2. list',
        '    1. ordered list',
        '    2. indented',
        '',
        '- [ ] task',
        '- [x] list completed',
        '',
        '[link](https://nhnent.github.io/tui.editor/)',
        '> block quote',
        '---',
        'horizontal line',
        '***',
        '`code`, *italic*, **bold**, ~~strikethrough~~, <span style="color:#e11d21">Red color</span>',
        '|table|head|',
        '|---|---|',
        '|table|body|'
      ].join('\n');

	 var el = document.querySelector('#mybutton');
      el.addEventListener('click', function(){
        console.log( editor.getValue());
      })
</script>
</body>
</html>