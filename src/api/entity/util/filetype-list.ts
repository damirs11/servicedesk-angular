export enum FILETYPE_MAP {
    zip = 'archive',
    rar = zip,  _7z = zip,
    gz  = zip,  jar = zip,
    gzip= zip,  tar = zip,
    tgz = zip,  tar_gz = zip,

    doc = 'word',
    dot = doc, wbk  = doc,
    docx= doc, docm = doc,
    dotm= doc, docb = doc,

    xls  = 'excel',
    xlt  = xls, xlm  = xls,
    xlsx = xls, xlsm = xls,
    xltx = xls, xltm = xls,
    xlsb = xls, xla  = xls,
    xlam = xls, xll  = xls,
    xlw  = xls,

    pdf = 'pdf',

    pptx = 'powerpoint',
    pptm = pptx, ppt  = pptx,
    xps  = pptx, potx = pptx,
    potm = pptx, pps  = pptx,
    ppa  = pptx, ppsx = pptx,

    png = 'image',
    jpg = png, jpeg= png,
    exif= png, gif = png,
    pbm = png, bmp = png,
    ppm = png, pgm = png,
    pnm = png, heif= png,
    bpg = png, tif = png,

    txt = 'text',
    md = txt, _1st = txt, me = txt,

    _3gp= 'audio',
    _aa = _3gp, _aac= _3gp,
    _aax= _3gp, _act= _3gp,
    au  = _3gp, mp3 = _3gp,
    mpc = _3gp, m4a = _3gp,
    ogg = _3gp, oga = _3gp,
    vox = _3gp, wav = _3gp,
    webm= _3gp, midi= _3gp,
}
