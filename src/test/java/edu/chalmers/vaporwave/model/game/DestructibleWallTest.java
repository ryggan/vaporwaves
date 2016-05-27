package edu.chalmers.vaporwave.model.game;

import org.junit.Test;
import sun.security.krb5.internal.crypto.Des;

import static org.junit.Assert.*;

/**
 * Created by FEngelbrektsson on 27/05/16.
 */
public class DestructibleWallTest {

    private DestructibleWall dWall = new DestructibleWall();

    @Test
    public void testDestroy() throws Exception {
        dWall.destroy(18.3);
        assertTrue(dWall.isDestroyed() == true);
    }

    @Test
    public void testIsDestroyed() throws Exception {
        dWall.destroy(18.3);
        assertTrue(dWall.isDestroyed() == true);
    }

    @Test
    public void testGetTimeStamp() throws Exception {
        dWall.destroy(18.3);
        assertTrue(dWall.getTimeStamp() == 18.3);
    }
}