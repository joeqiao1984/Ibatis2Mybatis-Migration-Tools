package com.github.iut.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class StringRegUtilsTest {
    @Test
    public void prepend1() {
        final String ret = StringRegUtils.prepend("1234", "5678");
        assertThat(ret).isEqualTo("5678 1234");
    }

    @Test
    public void prepend2() {
        final String ret = StringRegUtils.prepend(" 1234", "5678");
        assertThat(ret).isEqualTo(" 5678 1234");
    }

    @Test
    public void prepend3() {
        final String ret = StringRegUtils.prepend("   \n\n1234", "5678");
        assertThat(ret).isEqualTo("   \n\n5678 1234");
    }

    @Test
    public void prepend4() {
        final String ret = StringRegUtils.prepend("\r\n\r\n   \n1234", "5678");
        assertThat(ret).isEqualTo("\r\n\r\n   \n5678 1234");
    }

    @Test
    public void prepend5() {
        final String ret = StringRegUtils.prepend("\r\n\r\n   \n12 34", "5678");
        assertThat(ret).isEqualTo("\r\n\r\n   \n5678 12 34");
    }

    @Test
    public void append1() {
        final String ret = StringRegUtils.append("1234", "5678");
        assertThat(ret).isEqualTo("1234 5678");
    }

    @Test
    public void append2() {
        final String ret = StringRegUtils.append("1234 ", "5678");
        assertThat(ret).isEqualTo("1234 5678 ");
    }

    @Test
    public void append3() {
        final String ret = StringRegUtils.append("1234  \n\n", "5678");
        assertThat(ret).isEqualTo("1234 5678  \n\n");
    }

    @Test
    public void append4() {
        final String ret = StringRegUtils.append("1234    \r\n\n\r\n", "5678");
        assertThat(ret).isEqualTo("1234 5678    \r\n\n\r\n");
    }

    @Test
    public void append5() {
        final String ret = StringRegUtils.append("12 34    \r\n\n\r\n", "5678");
        assertThat(ret).isEqualTo("12 34 5678    \r\n\n\r\n");
    }

    @Test
    public void convertSharpVariable1() {
        assertThat(StringRegUtils.convertSharpVariable("abcd")).isEqualTo("abcd");
    }

    @Test
    public void convertSharpVariable2() {
        assertThat(StringRegUtils.convertSharpVariable("#abcd#")).isEqualTo("#{abcd}");
    }

    @Test
    public void convertSharpVariable3() {
        assertThat(StringRegUtils.convertSharpVariable("#abcd:asdf#")).isEqualTo("#{abcd}");
    }

    @Test
    public void convertSharpVariable4() {
        assertThat(StringRegUtils.convertSharpVariable("#abcd:VARCHAR#")).isEqualTo("#{abcd,jdbcType=VARCHAR}");
    }

    @Test
    public void convertSharpVariable5() {
        assertThat(StringRegUtils.convertSharpVariable("1234    #abcd# \n\n #abcd:asdf# \n\n #abcd:VARCHAR#")).isEqualTo("1234    #{abcd} \n\n #{abcd} \n\n #{abcd,jdbcType=VARCHAR}");
    }

    @Test
    public void convertSharpVariable6() {
        assertThat(StringRegUtils.convertSharpVariable("#abcd:VARCHAR2#")).isEqualTo("#{abcd,jdbcType=VARCHAR}");
    }

    @Test
    public void convertDollarVariable1() {
        assertThat(StringRegUtils.convertDollarVariable("asdf")).isEqualTo("asdf");
    }

    @Test
    public void convertDollarVariable2() {
        assertThat(StringRegUtils.convertDollarVariable("$asdf$")).isEqualTo("${asdf}");
    }

    @Test
    public void convertDollarVariable3() {
        assertThat(StringRegUtils.convertDollarVariable("asdf \n\n    $asdf$")).isEqualTo("asdf \n\n    ${asdf}");
    }
}