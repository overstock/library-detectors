library-detectors
=================


<p>
      Library detectors is a <a class="externalLink" href="http://findbugs.sourceforge.net/">FindBugs</a> plugin with detectors for certain
      patterns which could be considered bugs in library code. It currently detects the following bugs:
      </p><ul>
        <li>
          <b>GBU_GUAVA_BETA_CLASS_USAGE</b>
          Reference to a class annotated with
          <a class="externalLink" href="http://docs.guava-libraries.googlecode.com/git/javadoc/com/google/common/annotations/Beta.html">@Beta</a>
        </li>
        <li>
          <b>GBU_GUAVA_BETA_FIELD_USAGE</b>
          Reference to a field annotated with
          <a class="externalLink" href="http://docs.guava-libraries.googlecode.com/git/javadoc/com/google/common/annotations/Beta.html">@Beta</a>
        </li>
        <li>
          <b>GBU_GUAVA_BETA_METHOD_USAGE</b>
          Reference to a method annotated with
          <a class="externalLink" href="http://docs.guava-libraries.googlecode.com/git/javadoc/com/google/common/annotations/Beta.html">@Beta</a>
        </li>
      </ul>

See [the site page][Site] for more information.

[Site]: http://overstock.github.com/library-detectors/
