SUMMARY = "Regles iptables au demarrage"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://firewall"
S = "${WORKDIR}"

RDEPENDS:${PN} = "iptables"

inherit update-rc.d
INITSCRIPT_NAME = "firewall"
INITSCRIPT_PARAMS = "defaults"

do_install() {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/firewall ${D}${sysconfdir}/init.d/firewall
}

FILES:${PN} = "${sysconfdir}/init.d/firewall"
