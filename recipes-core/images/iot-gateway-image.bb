SUMMARY = "Image durcie pour passerelle IoT"
LICENSE = "MIT"

require recipes-core/images/core-image-base.bb

inherit extrausers

# hash genere avec: openssl passwd -6
ADMIN_PASSWORD_HASH ?= "\$6\$tZ5UVKhII1s8Jfjo\$D4hhW.aHCcPDA/PMiS1QTGeZSKe7NX454WYchiK2eFaB/C4ZBwiI6OL9mRrHIdLTMarGFSovJSdfX6nd/WAy/1"
EXTRA_USERS_PARAMS = "\
    groupadd -f sudo; \
    useradd -p '${ADMIN_PASSWORD_HASH}' -G sudo -s /bin/sh -m admin; \
    usermod -L root; \
"

IMAGE_FEATURES += "ssh-server-openssh read-only-rootfs"
IMAGE_FEATURES:remove = "debug-tweaks"

IMAGE_INSTALL:append = " iptables kernel-modules sudo firewall-config"

# autorise le groupe sudo (drop-in /etc/sudoers.d)
ROOTFS_POSTPROCESS_COMMAND += "add_admin_sudoers;"
add_admin_sudoers() {
    echo "%sudo ALL=(ALL) ALL" > ${IMAGE_ROOTFS}${sysconfdir}/sudoers.d/admin
    chmod 0440 ${IMAGE_ROOTFS}${sysconfdir}/sudoers.d/admin
}
