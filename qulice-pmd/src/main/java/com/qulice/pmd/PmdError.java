/*
 * SPDX-FileCopyrightText: Copyright (c) 2011-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.qulice.pmd;

import net.sourceforge.pmd.Report.ConfigurationError;
import net.sourceforge.pmd.Report.ProcessingError;
import net.sourceforge.pmd.RuleViolation;

/**
 * Represents one PMD error (usually it will be violation).
 *
 * @since 1.0
 */
public interface PmdError {
    /**
     * Returns error name which is short, fixed, human-readable category of
     * the error.
     * @return Error name.
     */
    String name();

    /**
     * Returns file name which caused this error.
     * May return sentinel value if file information is not available.
     * @return File name.
     */
    String fileName();

    /**
     * Returns formatted line range which cause this error.
     * May return sentinel value if line information is not available.
     * @return Formatted line range.
     */
    String lines();

    /**
     * Returns error description.
     * @return Description.
     */
    String description();

    /**
     * PmdError backed by a RuleViolation.
     * @since 1.0
     */
    final class OfRuleViolation implements PmdError {
        /**
         * Internal RuleViolation.
         */
        private final RuleViolation violation;

        /**
         * Creates a new PmdError, representing given RuleViolation.
         * @param violation Internal RuleViolation.
         */
        public OfRuleViolation(final RuleViolation violation) {
            this.violation = violation;
        }

        @Override
        public String name() {
            return this.violation.getRule().getName();
        }

        @Override
        public String fileName() {
            return this.violation.getFilename();
        }

        @Override
        public String lines() {
            return String.format(
                "%d-%d",
                this.violation.getBeginLine(), this.violation.getEndLine()
            );
        }

        @Override
        public String description() {
            return this.violation.getDescription();
        }
    }

    /**
     * PmdError backed by a ProcessingError.
     * @since 1.0
     */
    final class OfProcessingError implements PmdError {
        /**
         * Internal ProcessingError.
         */
        private final ProcessingError error;

        /**
         * Creates a new PmdError, representing given ProcessingError.
         * @param error Internal ProcessingError.
         */
        public OfProcessingError(final ProcessingError error) {
            this.error = error;
        }

        @Override
        public String name() {
            return "ProcessingError";
        }

        @Override
        public String fileName() {
            return this.error.getFile();
        }

        @Override
        public String lines() {
            return "unknown";
        }

        @Override
        public String description() {
            return new StringBuilder()
                .append(this.error.getMsg())
                .append(": ")
                .append(this.error.getDetail())
                .toString();
        }
    }

    /**
     * PmdError backed by a ConfigError.
     * @since 1.0
     */
    final class OfConfigError implements PmdError {
        /**
         * Internal ConfigError.
         */
        private final ConfigurationError error;

        /**
         * Creates a new PmdError, representing given ProcessingError.
         * @param error Internal ProcessingError.
         */
        public OfConfigError(final ConfigurationError error) {
            this.error = error;
        }

        @Override
        public String name() {
            return "ProcessingError";
        }

        @Override
        public String fileName() {
            return "unknown";
        }

        @Override
        public String lines() {
            return "unknown";
        }

        @Override
        public String description() {
            return this.error.issue();
        }
    }
}
