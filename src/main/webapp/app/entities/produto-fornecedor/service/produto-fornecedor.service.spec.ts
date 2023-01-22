import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProdutoFornecedor } from '../produto-fornecedor.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../produto-fornecedor.test-samples';

import { ProdutoFornecedorService } from './produto-fornecedor.service';

const requireRestSample: IProdutoFornecedor = {
  ...sampleWithRequiredData,
};

describe('ProdutoFornecedor Service', () => {
  let service: ProdutoFornecedorService;
  let httpMock: HttpTestingController;
  let expectedResult: IProdutoFornecedor | IProdutoFornecedor[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProdutoFornecedorService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a ProdutoFornecedor', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const produtoFornecedor = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(produtoFornecedor).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ProdutoFornecedor', () => {
      const produtoFornecedor = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(produtoFornecedor).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ProdutoFornecedor', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ProdutoFornecedor', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ProdutoFornecedor', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProdutoFornecedorToCollectionIfMissing', () => {
      it('should add a ProdutoFornecedor to an empty array', () => {
        const produtoFornecedor: IProdutoFornecedor = sampleWithRequiredData;
        expectedResult = service.addProdutoFornecedorToCollectionIfMissing([], produtoFornecedor);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(produtoFornecedor);
      });

      it('should not add a ProdutoFornecedor to an array that contains it', () => {
        const produtoFornecedor: IProdutoFornecedor = sampleWithRequiredData;
        const produtoFornecedorCollection: IProdutoFornecedor[] = [
          {
            ...produtoFornecedor,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProdutoFornecedorToCollectionIfMissing(produtoFornecedorCollection, produtoFornecedor);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ProdutoFornecedor to an array that doesn't contain it", () => {
        const produtoFornecedor: IProdutoFornecedor = sampleWithRequiredData;
        const produtoFornecedorCollection: IProdutoFornecedor[] = [sampleWithPartialData];
        expectedResult = service.addProdutoFornecedorToCollectionIfMissing(produtoFornecedorCollection, produtoFornecedor);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(produtoFornecedor);
      });

      it('should add only unique ProdutoFornecedor to an array', () => {
        const produtoFornecedorArray: IProdutoFornecedor[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const produtoFornecedorCollection: IProdutoFornecedor[] = [sampleWithRequiredData];
        expectedResult = service.addProdutoFornecedorToCollectionIfMissing(produtoFornecedorCollection, ...produtoFornecedorArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const produtoFornecedor: IProdutoFornecedor = sampleWithRequiredData;
        const produtoFornecedor2: IProdutoFornecedor = sampleWithPartialData;
        expectedResult = service.addProdutoFornecedorToCollectionIfMissing([], produtoFornecedor, produtoFornecedor2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(produtoFornecedor);
        expect(expectedResult).toContain(produtoFornecedor2);
      });

      it('should accept null and undefined values', () => {
        const produtoFornecedor: IProdutoFornecedor = sampleWithRequiredData;
        expectedResult = service.addProdutoFornecedorToCollectionIfMissing([], null, produtoFornecedor, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(produtoFornecedor);
      });

      it('should return initial array if no ProdutoFornecedor is added', () => {
        const produtoFornecedorCollection: IProdutoFornecedor[] = [sampleWithRequiredData];
        expectedResult = service.addProdutoFornecedorToCollectionIfMissing(produtoFornecedorCollection, undefined, null);
        expect(expectedResult).toEqual(produtoFornecedorCollection);
      });
    });

    describe('compareProdutoFornecedor', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProdutoFornecedor(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareProdutoFornecedor(entity1, entity2);
        const compareResult2 = service.compareProdutoFornecedor(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareProdutoFornecedor(entity1, entity2);
        const compareResult2 = service.compareProdutoFornecedor(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareProdutoFornecedor(entity1, entity2);
        const compareResult2 = service.compareProdutoFornecedor(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
