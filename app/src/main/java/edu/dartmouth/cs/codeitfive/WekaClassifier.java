package edu.dartmouth.cs.codeitfive;


class WekaClassifier {

  public static double classify(Object[] i)
      throws Exception {

    double p = Double.NaN;
    p = WekaClassifier.N3c2e200e26(i);
    return p;
  }
  static double N3c2e200e26(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 3;
    } else if (((Double) i[0]).doubleValue() <= 41.568108) {
      p = 3;
    } else if (((Double) i[0]).doubleValue() > 41.568108) {
      p = WekaClassifier.N783a95ff27(i);
    }
    return p;
  }
  static double N783a95ff27(Object []i) {
    double p = Double.NaN;
    if (i[64] == null) {
      p = 1;
    } else if (((Double) i[64]).doubleValue() <= 29.458753) {
      p = WekaClassifier.N4e4dd60328(i);
    } else if (((Double) i[64]).doubleValue() > 29.458753) {
      p = 2;
    }
    return p;
  }
  static double N4e4dd60328(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() <= 145.073642) {
      p = WekaClassifier.N581d871e29(i);
    } else if (((Double) i[2]).doubleValue() > 145.073642) {
      p = WekaClassifier.N5e44f8a534(i);
    }
    return p;
  }
  static double N581d871e29(Object []i) {
    double p = Double.NaN;
    if (i[64] == null) {
      p = 0;
    } else if (((Double) i[64]).doubleValue() <= 8.242976) {
      p = WekaClassifier.N7a91efc330(i);
    } else if (((Double) i[64]).doubleValue() > 8.242976) {
      p = WekaClassifier.N20d646f932(i);
    }
    return p;
  }
  static double N7a91efc330(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 2;
    } else if (((Double) i[2]).doubleValue() <= 15.659138) {
      p = 2;
    } else if (((Double) i[2]).doubleValue() > 15.659138) {
      p = WekaClassifier.N3142a1331(i);
    }
    return p;
  }
  static double N3142a1331(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 1;
    } else if (((Double) i[0]).doubleValue() <= 162.67517) {
      p = 1;
    } else if (((Double) i[0]).doubleValue() > 162.67517) {
      p = 0;
    }
    return p;
  }
  static double N20d646f932(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 595.677511) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 595.677511) {
      p = WekaClassifier.N53cd964633(i);
    }
    return p;
  }
  static double N53cd964633(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 52.107375) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 52.107375) {
      p = 1;
    }
    return p;
  }
  static double N5e44f8a534(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 1;
    } else if (((Double) i[0]).doubleValue() <= 576.842717) {
      p = WekaClassifier.N22048fd35(i);
    } else if (((Double) i[0]).doubleValue() > 576.842717) {
      p = WekaClassifier.N1597db2236(i);
    }
    return p;
  }
  static double N22048fd35(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 1;
    } else if (((Double) i[1]).doubleValue() <= 64.63691) {
      p = 1;
    } else if (((Double) i[1]).doubleValue() > 64.63691) {
      p = 0;
    }
    return p;
  }
  static double N1597db2236(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() <= 267.993594) {
      p = WekaClassifier.N24b3a04137(i);
    } else if (((Double) i[2]).doubleValue() > 267.993594) {
      p = WekaClassifier.Nec4868642(i);
    }
    return p;
  }
  static double N24b3a04137(Object []i) {
    double p = Double.NaN;
    if (i[32] == null) {
      p = 1;
    } else if (((Double) i[32]).doubleValue() <= 2.623876) {
      p = 1;
    } else if (((Double) i[32]).doubleValue() > 2.623876) {
      p = WekaClassifier.Ncde1e3a38(i);
    }
    return p;
  }
  static double Ncde1e3a38(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() <= 184.842684) {
      p = WekaClassifier.N4a92786f39(i);
    } else if (((Double) i[2]).doubleValue() > 184.842684) {
      p = 1;
    }
    return p;
  }
  static double N4a92786f39(Object []i) {
    double p = Double.NaN;
    if (i[7] == null) {
      p = 1;
    } else if (((Double) i[7]).doubleValue() <= 14.930743) {
      p = WekaClassifier.N5620e5a840(i);
    } else if (((Double) i[7]).doubleValue() > 14.930743) {
      p = 1;
    }
    return p;
  }
  static double N5620e5a840(Object []i) {
    double p = Double.NaN;
    if (i[8] == null) {
      p = 1;
    } else if (((Double) i[8]).doubleValue() <= 10.711597) {
      p = WekaClassifier.N9c5a4c541(i);
    } else if (((Double) i[8]).doubleValue() > 10.711597) {
      p = 0;
    }
    return p;
  }
  static double N9c5a4c541(Object []i) {
    double p = Double.NaN;
    if (i[22] == null) {
      p = 0;
    } else if (((Double) i[22]).doubleValue() <= 1.837082) {
      p = 0;
    } else if (((Double) i[22]).doubleValue() > 1.837082) {
      p = 1;
    }
    return p;
  }
  static double Nec4868642(Object []i) {
    double p = Double.NaN;
    if (i[64] == null) {
      p = 1;
    } else if (((Double) i[64]).doubleValue() <= 26.661839) {
      p = 1;
    } else if (((Double) i[64]).doubleValue() > 26.661839) {
      p = 2;
    }
    return p;
  }
}