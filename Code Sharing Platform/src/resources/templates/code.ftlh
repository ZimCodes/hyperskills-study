<!DOCTYPE html>
<html lang="en">
<head>
    <title>Code</title>
    <link rel="stylesheet"
          target="_blank" href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
</head>
<body>
<span id="load_date">${date}</span>
<pre id="code_snippet">
    <code>
        ${code}
    </code>
    </pre>
<#if time_restrict gt 0 && is_time_restricted>
    <span id="time_restriction">${time_restrict}</span>
</#if>
<#if views_restrict gte 0 && is_views_restricted>
    <span id="views_restriction">${views_restrict}</span>
</#if>
</body>
</html>