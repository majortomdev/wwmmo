package au.com.codeka.warworlds.server.admin.handlers;

import com.google.api.client.util.ByteStreams;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import au.com.codeka.warworlds.common.Log;
import au.com.codeka.warworlds.server.admin.RequestException;
import au.com.codeka.warworlds.server.admin.RequestHandler;

/** Simple handler for handling static files (and 'templated' HTML files with no templated data). */
public class FileHandler extends RequestHandler {
  private final Log log = new Log("AdminGenericHandler");

  @Override
  protected void get() throws RequestException {
    String path = getExtraOption();
    if (path == null) {
      path = "";
    }
    path += getUrlParameter("path");

    String contentType;
    if (path.endsWith(".css")) {
      contentType = "text/css";
    } else if (path.endsWith(".js")) {
      contentType = "text/javascript";
    } else if (path.endsWith(".png")) {
      contentType = "image/png";
    } else if (path.endsWith(".ico")) {
      contentType = "image/x-icon";
    } else {
      contentType = "text/plain";
    }
    getResponse().setContentType(contentType);
    getResponse().setHeader("Content-Type", contentType);

    try {
      OutputStream outs = getResponse().getOutputStream();
      InputStream ins = new FileInputStream(new File("data/admin/static/" + path));
      ByteStreams.copy(ins, outs);
      ins.close();
    } catch (IOException e) {
      log.error("Error sending static file!", e);
    }
  }
}
