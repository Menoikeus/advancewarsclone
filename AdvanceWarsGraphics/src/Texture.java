import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;

import de.matthiasmann.twl.utils.PNGDecoder;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Texture {
	public final int target = GL_TEXTURE_2D;
	int width, height;
	int id;

	public Texture() throws IOException {
		URL pngURL = new File("\\\\Students\\Home\\2017\\nguyendat\\Profile\\Downloads\\PNG_transparency_demonstration_1.png").toURI().toURL();
		InputStream input = null;
		try {
			input = pngURL.openStream();
			PNGDecoder decoder = new PNGDecoder(input);
			
			width = decoder.getWidth();
			height = decoder.getHeight();
			
			final int bpp = 4;
			
			ByteBuffer buffer = BufferUtils.createByteBuffer(bpp * width * height);
			
			decoder.decode(buffer, width * bpp,  PNGDecoder.Format.RGBA);
			
			buffer.flip();
			
			//GL texture creation
			
			glEnable(GL_TEXTURE_2D);
			id = glGenTextures();
			
			glBindTexture(GL_TEXTURE_2D, id);
			
			glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

			//*************
			//Setup filtering, i.e. how OpenGL will interpolate the pixels when scaling up or down
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

			//Setup wrap mode, i.e. how OpenGL will handle pixels outside of the expected range
			//Note: GL_CLAMP_TO_EDGE is part of GL12
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
			
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
			
		} finally {
            if (input != null) {
                try { input.close(); } catch (IOException e) { }
            }
        }
		
	}
	
	public void bind() {
        glBindTexture(target, id);
    }

}
