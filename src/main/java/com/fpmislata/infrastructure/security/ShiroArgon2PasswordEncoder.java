package com.fpmislata.infrastructure.security;

import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Codificador de contraseñas compatible con Apache Shiro 2 + Argon2id.
 *
 * Formato del hash almacenado en auth_user.password:
 *   $shiro2$argon2id$v=19$t=1,m=65536,p=4$<salt_base64_nopadding>$<hash_base64_nopadding>
 *
 * Parámetros:
 *   - Algoritmo: Argon2id
 *   - Versión: 19 (0x13)
 *   - Iteraciones: 1
 *   - Memoria: 65536 KB
 *   - Paralelismo: 4
 *   - Sal: 16 bytes aleatorios
 *   - Hash: 32 bytes
 *   - Codificación: Base64 sin padding
 */
@Component
public class ShiroArgon2PasswordEncoder {

    private static final int SALT_LENGTH = 16;
    private static final int HASH_LENGTH = 32;
    private static final int ITERATIONS = 1;
    private static final int MEMORY_KB = 65536;
    private static final int PARALLELISM = 4;

    private final SecureRandom secureRandom = new SecureRandom();

    /**
     * Genera el hash Argon2id de la contraseña en formato Shiro 2.
     *
     * @param rawPassword contraseña en texto plano
     * @return hash en formato $shiro2$argon2id$...
     */
    public String encode(String rawPassword) {
        byte[] salt = new byte[SALT_LENGTH];
        secureRandom.nextBytes(salt);

        Argon2Parameters params = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
                .withVersion(Argon2Parameters.ARGON2_VERSION_13)
                .withIterations(ITERATIONS)
                .withMemoryAsKB(MEMORY_KB)
                .withParallelism(PARALLELISM)
                .withSalt(salt)
                .build();

        Argon2BytesGenerator generator = new Argon2BytesGenerator();
        generator.init(params);

        byte[] hash = new byte[HASH_LENGTH];
        generator.generateBytes(rawPassword.toCharArray(), hash);

        Base64.Encoder encoder = Base64.getEncoder().withoutPadding();
        return "$shiro2$argon2id$v=19$t=1,m=65536,p=4$"
                + encoder.encodeToString(salt)
                + "$"
                + encoder.encodeToString(hash);
    }
}
