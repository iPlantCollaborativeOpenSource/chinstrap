function download(filename, data) {
    var blob = new Blob([data]);
    var evt = document.createEvent("HTMLEvents");
        evt.initEvent("click");
        $("<a>", {
            download: filename,
            href: webkitURL.createObjectURL(blob)
            }).get(0).dispatchEvent(evt);
};
