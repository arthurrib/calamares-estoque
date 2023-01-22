import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../produto-fornecedor.test-samples';

import { ProdutoFornecedorFormService } from './produto-fornecedor-form.service';

describe('ProdutoFornecedor Form Service', () => {
  let service: ProdutoFornecedorFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProdutoFornecedorFormService);
  });

  describe('Service methods', () => {
    describe('createProdutoFornecedorFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProdutoFornecedorFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            valor: expect.any(Object),
            observacoes: expect.any(Object),
            produto: expect.any(Object),
            fornecedor: expect.any(Object),
            produtoFornecedor: expect.any(Object),
          })
        );
      });

      it('passing IProdutoFornecedor should create a new form with FormGroup', () => {
        const formGroup = service.createProdutoFornecedorFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            valor: expect.any(Object),
            observacoes: expect.any(Object),
            produto: expect.any(Object),
            fornecedor: expect.any(Object),
            produtoFornecedor: expect.any(Object),
          })
        );
      });
    });

    describe('getProdutoFornecedor', () => {
      it('should return NewProdutoFornecedor for default ProdutoFornecedor initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createProdutoFornecedorFormGroup(sampleWithNewData);

        const produtoFornecedor = service.getProdutoFornecedor(formGroup) as any;

        expect(produtoFornecedor).toMatchObject(sampleWithNewData);
      });

      it('should return NewProdutoFornecedor for empty ProdutoFornecedor initial value', () => {
        const formGroup = service.createProdutoFornecedorFormGroup();

        const produtoFornecedor = service.getProdutoFornecedor(formGroup) as any;

        expect(produtoFornecedor).toMatchObject({});
      });

      it('should return IProdutoFornecedor', () => {
        const formGroup = service.createProdutoFornecedorFormGroup(sampleWithRequiredData);

        const produtoFornecedor = service.getProdutoFornecedor(formGroup) as any;

        expect(produtoFornecedor).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProdutoFornecedor should not enable id FormControl', () => {
        const formGroup = service.createProdutoFornecedorFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProdutoFornecedor should disable id FormControl', () => {
        const formGroup = service.createProdutoFornecedorFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
