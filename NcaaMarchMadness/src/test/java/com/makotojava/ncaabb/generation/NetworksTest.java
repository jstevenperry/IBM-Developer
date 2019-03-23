package com.makotojava.ncaabb.generation;

import com.makotojava.ncaabb.generation.Networks;
import org.junit.Assert;
import org.junit.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.fail;

@RunWith(JUnitPlatform.class)
public class NetworksTest {

  @Test
  public void testGoodLayerStructureDefinitions() {
    String networkDefinition = "23x14x7";
    int[] expected = new int[]{
      23, 14, 7
    };
    int[] actual = Networks.parseNetworkStructure(networkDefinition);
    Assert.assertArrayEquals(expected, actual);

    networkDefinition = "23x14x7x5x43x3245";
    expected = new int[]{
      23, 14, 7, 5, 43, 3245
    };
    actual = Networks.parseNetworkStructure(networkDefinition);
    Assert.assertArrayEquals(expected, actual);
  }

  @Test
  public void testBadLayerStructureDefinitions() {
    String networkDefinition = "23Ax14x7";
    try {
      int[] actual = Networks.parseNetworkStructure(networkDefinition);
      fail(String.format("Expected a RuntimeException when parsing bad network definition %s", networkDefinition));
    } catch (RuntimeException e) {
      // Expected
    }

    networkDefinition = "Ax7";
    try {
      int[] actual = Networks.parseNetworkStructure(networkDefinition);
      fail(String.format("Expected a RuntimeException when parsing bad network definition %s", networkDefinition));
    } catch (RuntimeException e) {
      // Expected
    }

  }
}
