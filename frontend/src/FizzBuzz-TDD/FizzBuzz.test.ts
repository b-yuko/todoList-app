import { fizzBuzz } from "@/FizzBuzz-TDD/FizzBuzz.ts";
import { describe, expect } from "vitest";

describe("Fizz Buzz 数列と変換規則を扱う fizzBuzz関数", () => {
  describe("fizzBuzz関数は、数を文字列に変換する", () => {
    describe("3と5の両方の倍数のときは数の代わりに「FizzBuzz」に変換する", () => {
      test("15を渡すと文字列 FizzBuzz を返す", () => {
        // Given

        // When
        const result = fizzBuzz(15);

        // Then
        expect(result).toBe("FizzBuzz");
      });
    });
    describe("3の倍数のときは数の代わりに「Fizz」に変換する", () => {
      test("3を渡すと文字列 Fizz を返す", () => {
        // Given

        // When
        const result = fizzBuzz(3);

        // Then
        expect(result).toBe("Fizz");
      });
    });
    describe("5の倍数のときは数の代わりに「Buzz」に変換する", () => {
      test("5を渡すと文字列 Buzz を返す", () => {
        // Given

        // When
        const result = fizzBuzz(5);

        // Then
        expect(result).toBe("Buzz");
      });
    });
    describe("その他の数のときはそのまま文字列に変換する", () => {
      test("1を渡すと文字列1を返す", () => {
        // Given

        // When
        const result = fizzBuzz(1);

        // Then
        expect(result).toBe("1");
      });
    });
  });
});
