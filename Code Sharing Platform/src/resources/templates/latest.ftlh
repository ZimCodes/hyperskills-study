<!DOCTYPE html>
<html lang="en">
<head>
    <title>Latest</title>
    <link rel="stylesheet"
          target="_blank" href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
</head>
<body>
<#list records as record>
    <#list record as key, val>
        <#if key == "date">
            <span id="load_date">${val}</span>
        <#else>
            <pre id="code_snippet">
                <code>
                ${val}
                </code>
            </pre>
        </#if>
    </#list>
</#list>
</body>
</html>